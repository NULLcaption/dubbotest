package com.cxg.common.core;

import lombok.SneakyThrows;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

/**
 * ZK序列化
 * 要是使用默认的SerializableSerializer
 * 会有乱码或者java.io.EOFException错误
 */
public class MyZkSerializer implements ZkSerializer {

    @SneakyThrows
    @Override
    public byte[] serialize(Object o) throws ZkMarshallingError {
        return String.valueOf(o).getBytes("utf-8");
    }

    @SneakyThrows
    @Override
    public Object deserialize(byte[] bytes) throws ZkMarshallingError {
        return new String(bytes, "utf-8");
    }
}
