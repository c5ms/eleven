package com.eleven.access.core;


import java.io.Closeable;
import java.util.Collection;
import java.util.Map;

/**
 * 负责执行同步,从源读取数据,然后写入到writer中.
 * 同步实现是有状态的.
 */
public interface RecordReader extends Closeable {

    /**
     * 设置同步目标
     *
     * @param channel 写入
     * @param config  配置
     * @param runtime 运行环境
     */
    void start(RecordChannel channel, Map<String, String> config, Map<String, String> runtime) throws Exception;

    /**
     * 获取记录描述元数据,此方法不需要执行start即可执行,默认返回空的列表.
     *
     * @return 记录描述
     * @throws Exception 读取终端资源的数据列信息的时候抛出异常
     */
    Collection<Schema> readSchemas(Map<String, String> config) throws Exception;


}
