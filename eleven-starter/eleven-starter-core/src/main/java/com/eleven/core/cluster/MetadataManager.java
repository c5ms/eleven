package com.eleven.core.cluster;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * 元数据管理器，元数据指的是类似缓存的一种东西，但是一般来说会有一些场景化的使用方式，来实现某种设计模式。
 * 此处提供的元数据存储机制不保证持久化机制！注意，这里的元数据管理不支持持久化！都是闪存元数据，或者说是运行时数据。
 * <p>
 * 场景 1：比如用户请求进入的时候，检测一下全局的某种机制配置版本，如果本地版本和远程版本不一致，则更新本地版本，保证得到最新配置。
 * 场景 2：存储某一个任务的执行状态
 * 场景 3：全局存储服务器节点在线状态
 */
public interface MetadataManager {
    /**
     * 设置一个元数据
     *
     * @param name 元数据名
     * @param data 元数据值
     * @return 元数据对象
     */
    Metadata put(String name, String data);

    /**
     * 读取一个元数据
     *
     * @param name 元数据名
     * @return 元数据对象，永远都不会返回空，如果不存在，返回一个值为空的对象
     */
    Metadata get(String name);

    /**
     * 运行时属性信息记录
     *
     * @param owner       持有着
     * @param property    信息属性
     * @param information 信息内容
     */
    void putRuntimeProperty(String owner, String property, Serializable information);

    /**
     * 运行时属性信息读取
     *
     * @param owner    持有着
     * @param property 信息属性
     * @param clazz    对象类型
     */
    <T> T getRuntimeProperty(String owner, String property, Class<T> clazz);

    /**
     * 更新全局版本
     *
     * @param name 元数据名
     * @return 版本
     */
    String updateGlobalVersion(String name);

    /**
     * 读取全局版本
     *
     * @param name 元数据名
     * @return 版本
     */
    String getGlobalVersion(String name);

    /**
     * 使用指定值更新版本号
     *
     * @param name    元数据名
     * @param version 版本号
     */
    void updateLocalVersion(String name, String version);

    /**
     * 读取一个本地版本
     *
     * @param name 元数据名
     * @return 版本值
     */
    String getLocalVersion(String name);

    /**
     * 同步一个本地版本
     *
     * @param key      元数据名
     * @param onChange 如果版本发生变化，需要进行的响应
     */
    void syncVersion(String key, Consumer<String> onChange);

    /**
     * 仅仅同步版本，不做任何处理
     *
     * @param key 元数据名
     */
    void syncVersion(String key);
}
