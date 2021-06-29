package com.cxg.common;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.FinalRequestProcessor;
import org.apache.zookeeper.server.NIOServerCnxn;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 测试zk watcher监听过程
 */
public class WatcherDemo implements Watcher {
    public Logger logger = LoggerFactory.getLogger(WatcherDemo.class);
    //zookeeper连接地址
    private static String ZOOKEEPER_PATH = "localhost:2181";
    static ZooKeeper zooKeeper;
    //初始化连接
    static {
        try {
            //创建一个 ZooKeeper 客户端对象实例时，可以向构造方法中传入一个默认的 Watcher
            zooKeeper = new ZooKeeper(ZOOKEEPER_PATH, 4000, new WatcherDemo());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void process(WatchedEvent watchedEvent) {
        logger.info("eventType:"+watchedEvent.getType());
        if(watchedEvent.getType() == Event.EventType.NodeDataChanged) {
            try {
                zooKeeper.exists(watchedEvent.getPath(), true);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws KeeperException, InterruptedException, IOException {
        //读取配置的文件路径
        String CONF_PATH = "/zookeeper/watch";
        if (zooKeeper.exists(CONF_PATH, false) == null) {
            zooKeeper.create(CONF_PATH, "0".getBytes("utf-8"), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        Thread.sleep(1000);
        System.out.println("-------------");
        Stat stat = zooKeeper.exists(CONF_PATH, true);
        System.in.read();
    }
}
