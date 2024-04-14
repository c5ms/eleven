package com.eleven.access.core;

public interface RecordConverter {
    /**
     * 转换消息
     *
     * @param record 消息
     * @return 新消息
     */
    java.lang.Record convert(java.lang.Record record) throws RecordConvertException;
}
