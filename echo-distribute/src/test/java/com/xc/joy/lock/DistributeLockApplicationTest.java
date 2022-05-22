package com.xc.joy.lock;

import com.xc.joy.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.KeeperException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * @author lxcecho 909231497@qq.com
 * @since 22.05.2022
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class DistributeLockApplicationTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void concurrentOrder() throws InterruptedException {
        Thread.sleep(60000);
        // 创建闭锁
        CountDownLatch cdl = new CountDownLatch(5);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        ExecutorService es = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            es.execute(() -> {
                try {
                    // 让所有的线程在这里等待，同时去执行下边创建订单的过程
                    cyclicBarrier.await();
                    Integer orderId = orderService.createOrder0();
//                    Integer orderId = orderService.createOrder();
                    System.out.println("订单id = " + orderId);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 每个线程执行完 1-
                    cdl.countDown();
                }
            });
        }
        cdl.await();
        es.shutdown();
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void testZkLock() throws IOException {
        ZkLock2 zkLock2 = new ZkLock2("localhost:2181", "order");
        try {
            zkLock2.getLock();
            zkLock2.close();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testZkLock2() throws Exception {
        ZkLock zkLock = new ZkLock();
        boolean lock = zkLock.getLock("order");
        log.info("获得锁的结果：" + lock);

        zkLock.close();
    }

    @Test
    public void testCuratorLock() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", retryPolicy);
        client.start();
        InterProcessMutex lock = new InterProcessMutex(client, "/order");
        try {
            if (lock.acquire(30, TimeUnit.SECONDS)) {
                try {
                    log.info("我获得了锁！！！");
                } finally {
                    lock.release();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.close();
    }

    @Test
    public void testRedissonLock() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.73.130:6379");
        RedissonClient redisson = Redisson.create(config);

        RLock rLock = redisson.getLock("order");

        try {
            rLock.lock(30, TimeUnit.SECONDS);
            log.info("我获得了锁！！！");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.info("我释放了锁！！");
            rLock.unlock();
        }
    }

}
