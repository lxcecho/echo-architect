package com.xc.joy;

import com.xc.joy.lock.ZkLock;
import org.apache.zookeeper.KeeperException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EchoArchitectApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Test
    public void testZkLock() throws IOException {
        ZkLock zkLock = new ZkLock();
        try {
            zkLock.getLock("order");
            zkLock.close();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
