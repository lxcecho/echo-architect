package com.xc.joy.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * @author lxcecho 909231497@qq.com
 * @since 15.05.2022
 */
@Slf4j
public class ZkLock implements Watcher, AutoCloseable {

    private ZooKeeper zooKeeper;

    private String businessName;

    private String znode;

    public ZkLock(String connectString, String businessName) throws IOException {
        this.zooKeeper = new ZooKeeper(connectString, 30000, this);
        this.businessName = businessName;
    }

    public boolean getLock() throws InterruptedException, KeeperException {
        Stat existsNode = zooKeeper.exists("/" + businessName, false);
        if (existsNode == null) {
            zooKeeper.create("/" + businessName, businessName.getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
        }

        znode = zooKeeper.create("/" + businessName + "/" + businessName + "_", businessName.getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        znode = znode.substring(znode.lastIndexOf("/") + 1);
        List<String> childrenNodes = zooKeeper.getChildren("/" + businessName, false);
        Collections.sort(childrenNodes);
        String firstNode = childrenNodes.get(0);
        if (!firstNode.equals(znode)) {
            String lastNode = firstNode;
            for (String node : childrenNodes) {
                if (!znode.equals(node)) {
                    lastNode = node;
                } else {
                    zooKeeper.exists("/" + businessName + "/" + lastNode, true);
                    break;
                }
            }
            synchronized (this) {
                wait();
            }
        }
        return true;
    }

    @Override
    public void close() throws Exception {
        zooKeeper.delete("/" + businessName + "/" + znode, -1);
        zooKeeper.close();
        log.info("release lock");
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.NodeDeleted) {
            synchronized (this) {
                notify();
            }
        }
    }

}