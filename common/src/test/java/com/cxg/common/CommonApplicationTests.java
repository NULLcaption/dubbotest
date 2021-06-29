package com.cxg.common;

import com.cxg.common.core.MyZkSerializer;
import com.cxg.common.core.ZookeeperConnector;
import org.I0Itec.zkclient.ZkClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;

//@SpringBootTest
class CommonApplicationTests{
    //读取配置的文件路径
    private static final String CONF_PATH = "/zookeeper/configTest";
    //zookeeper连接地址
    private static final String ZOOKEEPER_PATH = "localhost:2181";

    /**
     * 初始化ZK，并写入数据
     */
    @Test
    public void init() {
        ZkClient client = new ZkClient(ZOOKEEPER_PATH, 1000,1000, new MyZkSerializer());
        if (client.exists(CONF_PATH)) {
            client.delete(CONF_PATH);
        }
        client.createPersistent(CONF_PATH);
        client.writeData(CONF_PATH, "192.168.0.1");
    }

    @Test
    void contextLoads() {
        Thread t1 = new Thread(new ZookeeperConnector());
        Thread t2 = new Thread(new ZookeeperConnector());
        Thread t3 = new Thread(new ZookeeperConnector());
        Thread t4 = new Thread(new ZookeeperConnector());
        Thread t5 = new Thread(new ZookeeperConnector());

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        try {
            Thread.currentThread().join();//主程等待子线程结束
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
