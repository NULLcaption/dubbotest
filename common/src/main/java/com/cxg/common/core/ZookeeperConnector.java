package com.cxg.common.core;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * zookeeper连接器
 * 测试通过读取zk文件中的ip配置来热加载DBIP
 * 1.连接zk，获取到初始的ip地址；
 * 2.给CONF_PATH加上watch监听
 */
public class ZookeeperConnector implements Runnable {

    //读取配置的文件路径
    private static final String CONF_PATH = "/zookeeper/configTest";
    //zookeeper连接地址
    private static final String ZOOKEEPER_PATH = "127.0.0.1:2181";
    //初始化连接
    private ZkClient client = new ZkClient(ZOOKEEPER_PATH, 1000,1000, new MyZkSerializer());

    private static Logger logger = LoggerFactory.getLogger(ZookeeperConnector.class);

    private String dbIp;

    @Override
    public void run() {
        //读取文件中配置的P地址
        dbIp = client.readData(CONF_PATH);

        logger.info(Thread.currentThread().getName()+"获取到的IP地址为"+dbIp);

        IZkDataListener iZkDataListener = new IZkDataListener() {
            //节点数据别删除
            @Override
            public void handleDataDeleted(String s) throws Exception {
                logger.info("---------node delete-----------");
            }
            //节点数据发生改变
            //当事件节点发生变化时，客户端获取最新的的配置信息
            @Override
            public void handleDataChange(String stringPath, Object data) throws Exception {
                if (data == null) {
                    return;
                }
                if(!dbIp.equals(data.toString())) {
                    dbIp = client.readData(CONF_PATH);
                    logger.info(Thread.currentThread().getName()+"获取到的IP地址为"+dbIp);
                }
            }
        };
        //给事件节点增加监听器
        client.subscribeDataChanges(CONF_PATH, iZkDataListener);
    }

}
