package com.eleven.core.cluster;

/**
 * 系统元数据读写器，这个接口的实现类就一个要求： 每秒支持百万次读，万次以上的写
 */
public interface MetadataStore {

    Metadata put(String name, String data);

    Metadata get(String name);

}
