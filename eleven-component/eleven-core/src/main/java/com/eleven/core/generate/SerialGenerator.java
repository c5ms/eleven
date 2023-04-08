package com.eleven.core.generate;


/**
 * 自增序列生成器
 * 注意： 这个接口的任何实现逻辑都不可能有极高的效率，所以不是在很特殊的情况下不要使用，除非业务必须要有序的序列
 */
public interface SerialGenerator {

    long nextSerial(String schema);

    default long nextSerial(Class<?> schema) {
        return nextSerial(schema.getSimpleName());
    }
}
