package com.xc.joy.service;

import com.xc.joy.mapper.order.OrderItemMapper;
import com.xc.joy.mapper.order.OrderMapper;
import com.xc.joy.mapper.order.ProductMapper;
import com.xc.joy.model.order.Order;
import com.xc.joy.model.order.OrderItem;
import com.xc.joy.model.order.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private ProductMapper productMapper;

    //购买商品id
    private int purchaseProductId = 100100;

    //购买商品数量
    private int purchaseProductNum = 1;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private TransactionDefinition transactionDefinition;

    private Lock lock = new ReentrantLock();

    /**
     * 使用 synchronized 块
     *
     * @return
     * @throws Exception
     */
    public synchronized Integer createOrder2() throws Exception {
        Product product = null;
        TransactionStatus transactionStatus1 = platformTransactionManager.getTransaction(transactionDefinition);
        synchronized (this) {
            product = productMapper.selectByPrimaryKey(purchaseProductId);
            if (product == null) {
                // 事务回滚
                platformTransactionManager.rollback(transactionStatus1);
                throw new Exception("购买商品：" + purchaseProductId + "不存在");
            }
            //商品当前库存
            Integer currentCount = product.getCount();
            System.out.println(Thread.currentThread().getName() + "库存数：" + currentCount);
            //校验库存
            if (purchaseProductNum > currentCount) {
                // 事务回滚
                platformTransactionManager.rollback(transactionStatus1);
                throw new Exception("商品" + purchaseProductId + "仅剩" + currentCount + "件，无法购买");
            }
            // 更变换为使用数据库的行锁
            productMapper.updateProductCount(purchaseProductNum, "lxcecho", new Date(), product.getId());
            platformTransactionManager.commit(transactionStatus1);
        }

        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
        Order order = new Order();
        order.setOrderAmount(product.getPrice().multiply(new BigDecimal(purchaseProductNum)));
        order.setOrderStatus(1);//待处理
        order.setReceiverName("lxcecho");
        order.setReceiverMobile("13311112222");
        order.setCreateTime(new Date());
        order.setCreateUser("lxcecho");
        order.setUpdateTime(new Date());
        order.setUpdateUser("lxcecho");
        orderMapper.insertSelective(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(product.getId());
        orderItem.setPurchasePrice(product.getPrice());
        orderItem.setPurchaseNum(purchaseProductNum);
        orderItem.setCreateUser("lxcecho");
        orderItem.setCreateTime(new Date());
        orderItem.setUpdateTime(new Date());
        orderItem.setUpdateUser("lxcecho");
        orderItemMapper.insertSelective(orderItem);
        // 事务提交
        platformTransactionManager.commit(transactionStatus);
        return order.getId();
    }

    /**
     * 方法加入 synchronized 关键字，需要把事务也要锁住，即手动管理事务
     *
     * @return
     * @throws Exception
     */
//    @Transactional(rollbackFor = Exception.class)
    public synchronized Integer createOrder1() throws Exception {
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);

        Product product = null;
        product = productMapper.selectByPrimaryKey(purchaseProductId);
        if (product == null) {
            // 事务回滚
            platformTransactionManager.rollback(transactionStatus);
            throw new Exception("购买商品：" + purchaseProductId + "不存在");
        }

        //商品当前库存
        Integer currentCount = product.getCount();
        System.out.println(Thread.currentThread().getName() + "库存数：" + currentCount);
        //校验库存
        if (purchaseProductNum > currentCount) {
            // 事务回滚
            platformTransactionManager.rollback(transactionStatus);
            throw new Exception("商品" + purchaseProductId + "仅剩" + currentCount + "件，无法购买");
        }

        // 更变换为使用数据库的行锁
        productMapper.updateProductCount(purchaseProductNum, "lxcecho", new Date(), product.getId());
        // todo 不用锁的方案： 更新完后再次检索商品库存，为负数的话抛出异常

        Order order = new Order();
        order.setOrderAmount(product.getPrice().multiply(new BigDecimal(purchaseProductNum)));
        order.setOrderStatus(1);//待处理
        order.setReceiverName("lxcecho");
        order.setReceiverMobile("13311112222");
        order.setCreateTime(new Date());
        order.setCreateUser("lxcecho");
        order.setUpdateTime(new Date());
        order.setUpdateUser("lxcecho");
        orderMapper.insertSelective(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(product.getId());
        orderItem.setPurchasePrice(product.getPrice());
        orderItem.setPurchaseNum(purchaseProductNum);
        orderItem.setCreateUser("lxcecho");
        orderItem.setCreateTime(new Date());
        orderItem.setUpdateTime(new Date());
        orderItem.setUpdateUser("lxcecho");
        orderItemMapper.insertSelective(orderItem);
        // 事务提交
        platformTransactionManager.commit(transactionStatus);
        return order.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer createOrder0() throws Exception {
        Product product = null;
        product = productMapper.selectByPrimaryKey(purchaseProductId);
        if (product == null) {
            throw new Exception("购买商品：" + purchaseProductId + "不存在");
        }

        //商品当前库存
        Integer currentCount = product.getCount();
        System.out.println(Thread.currentThread().getName() + "库存数：" + currentCount);
        //校验库存
        if (purchaseProductNum > currentCount) {
            throw new Exception("商品" + purchaseProductId + "仅剩" + currentCount + "件，无法购买");
        }
        /*// 计算剩余库存
        int leftCount = currentCount - purchaseProductNum;
        // 更新库存
        product.setCount(leftCount);
        product.setUpdateTime(new Date());
        product.setUpdateUser("lxcecho");
        productMapper.updateByPrimaryKeySelective(product);*/

        // 更变换为使用数据库的行锁
        productMapper.updateProductCount(purchaseProductNum, "lxcecho", new Date(), product.getId());
        // todo 不用锁的方案： 更新完后再次检索商品库存，为负数的话抛出异常

        Order order = new Order();
        order.setOrderAmount(product.getPrice().multiply(new BigDecimal(purchaseProductNum)));
        order.setOrderStatus(1);//待处理
        order.setReceiverName("lxcecho");
        order.setReceiverMobile("13311112222");
        order.setCreateTime(new Date());
        order.setCreateUser("lxcecho");
        order.setUpdateTime(new Date());
        order.setUpdateUser("lxcecho");
        orderMapper.insertSelective(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(product.getId());
        orderItem.setPurchasePrice(product.getPrice());
        orderItem.setPurchaseNum(purchaseProductNum);
        orderItem.setCreateUser("lxcecho");
        orderItem.setCreateTime(new Date());
        orderItem.setUpdateTime(new Date());
        orderItem.setUpdateUser("lxcecho");
        orderItemMapper.insertSelective(orderItem);
        return order.getId();
    }

    /**
     * 使用 ReentrantLock 加锁
     *
     * @return
     * @throws Exception
     */
    public Integer createOrder() throws Exception {
        Product product = null;
        lock.lock();
        try {
            TransactionStatus transaction1 = platformTransactionManager.getTransaction(transactionDefinition);
            product = productMapper.selectByPrimaryKey(purchaseProductId);
            if (product == null) {
                platformTransactionManager.rollback(transaction1);
                throw new Exception("购买商品：" + purchaseProductId + "不存在");
            }

            //商品当前库存
            Integer currentCount = product.getCount();
            System.out.println(Thread.currentThread().getName() + "库存数：" + currentCount);
            //校验库存
            if (purchaseProductNum > currentCount) {
                platformTransactionManager.rollback(transaction1);
                throw new Exception("商品" + purchaseProductId + "仅剩" + currentCount + "件，无法购买");
            }

            productMapper.updateProductCount(purchaseProductNum, "lxcecho", new Date(), product.getId());
            platformTransactionManager.commit(transaction1);
        } finally {
            lock.unlock();
        }

        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        Order order = new Order();
        order.setOrderAmount(product.getPrice().multiply(new BigDecimal(purchaseProductNum)));
        order.setOrderStatus(1);//待处理
        order.setReceiverName("lxcecho");
        order.setReceiverMobile("13311112222");
        order.setCreateTime(new Date());
        order.setCreateUser("lxcecho");
        order.setUpdateTime(new Date());
        order.setUpdateUser("lxcecho");
        orderMapper.insertSelective(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(product.getId());
        orderItem.setPurchasePrice(product.getPrice());
        orderItem.setPurchaseNum(purchaseProductNum);
        orderItem.setCreateUser("lxcecho");
        orderItem.setCreateTime(new Date());
        orderItem.setUpdateTime(new Date());
        orderItem.setUpdateUser("lxcecho");
        orderItemMapper.insertSelective(orderItem);
        platformTransactionManager.commit(transaction);
        return order.getId();
    }

}
