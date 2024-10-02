package com.eleven.access.core;

import java.util.Date;

/**
 * 结构化数据记录的列
 */
public interface RecordValue {
    /**
     * 获取类型
     *
     * @return 列类型
     */
    Type getType();

    /**
     * 获取原始数据
     *
     * @return 原始数据
     */
    Object getRaw();

    /**
     * 转换为一个值,这个值比较特殊,是列按照自己的类型输出的默认格式,用于转换为可传输的数据
     *
     * @return 转换的值
     */
    Object asValue();

    /**
     * 转换为long
     *
     * @return 转换的值
     */
    Long asLong();

    /**
     * 转换为浮点值
     *
     * @return 转换的值
     */
    Double asDouble();

    /**
     * 转换为字符串
     *
     * @return 转换的值
     */
    String asString();

    /**
     * 转换为布尔型
     *
     * @return 转换的值
     */
    Boolean asBoolean();

    /**
     * 转换为字节数组
     *
     * @return 转换的值
     */
    byte[] asBytes();

    /**
     * 转换为日期
     *
     * @return 转换的值
     */
    Date asDate();

    /**
     * 列类型
     */
    enum Type {
        BAD,
        NULL,
        LONG,
        DOUBLE,
        STRING,
        BOOL,
        DATE,
        BYTES
    }


}
