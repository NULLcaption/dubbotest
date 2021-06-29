package com.cxg.common;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Zookeeper分布式锁实现
 * 1.排它锁：只写锁
 * 2.共享锁：只读锁
 */
public class TestZkLock {
    public static void main(String[] args){
        CuratorFramework zkClient = getZkClient();
        //读取配置的文件路径
        String CONF_PATH = "/zookeeper/lock";
        InterProcessMutex lock = new InterProcessMutex(zkClient, CONF_PATH);
        for (int i = 0; i < 50; i++) {
            new Thread(new TestThread(i, lock)).start();
        }
    }

    /**
     * 测试线程
     */
    static class TestThread implements Runnable{
        public Logger logger = LoggerFactory.getLogger(TestThread.class);
        private Integer threadFlag;
        private InterProcessMutex lock;

        public TestThread (Integer threadFlag, InterProcessMutex lock) {
            this.threadFlag = threadFlag;
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                lock.acquire();
                logger.info(Thread.currentThread().getName()+"第"+threadFlag+"获得锁");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    lock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 初始化ZK客户端
     * @return
     */
    private static CuratorFramework getZkClient() {
        //zookeeper连接地址
        String ZOOKEEPER_PATH = "localhost:2181";

        ExponentialBackoffRetry retry = new ExponentialBackoffRetry(1000, 3, 5000);
        CuratorFramework zkClient = CuratorFrameworkFactory.builder()
                .connectString(ZOOKEEPER_PATH)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retry)
                .build();
        zkClient.start();
        return zkClient;

    }
}
