package com.cxg.common;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class CommonApplicationTests implements Watcher {

    private static ZooKeeper zookeeper;
    @Test
    void contextLoads() throws InterruptedException, IOException {
        zookeeper = new ZooKeeper("192.168.99.100:32803",5000,new CommonApplicationTests());
        System.out.println(zookeeper.getState());

        Thread.sleep(Integer.MAX_VALUE);
    }
    private void doSomething(){
        System.out.println("do something");
    }

    @Override
    public void process(WatchedEvent event) {

        System.out.println("收到事件："+event);
        if (event.getState()== Event.KeeperState.SyncConnected){

            if (event.getType()== Event.EventType.None && null==event.getPath()){
                doSomething();
            }
        }
    }
}
