package com.eleven.access.core;

import java.io.Serializable;
import java.util.Map;

/**
 * 结构化数据记录
 */
public interface Record extends Serializable {
    /**
     * 获取数据的字符集
     *
     * @return 字符集
     */
    String getCharset();

    /**
     * 转换成普通json字符串
     *
     * @return json字符串
     */
    String asJson();

    /**
     * 放入一个列
     *
     * @param name   列名
     * @param column 列值
     */
    void putColumn(String name, RecordValue column);

    /**
     * 取出一个列
     *
     * @param name 列名
     * @return 列值
     */
    RecordValue getColumn(String name);

    /**
     * 获取所有的列集合
     *
     * @return 所有的列
     */
    Map<String, RecordValue> getData();

    /**
     * 获取数据集合名称,比如是: 数据表名,es索引名
     *
     * @return 数据集名称
     */
    Schema getSchema();

}
