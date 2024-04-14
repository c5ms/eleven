package com.eleven.access.core;

import com.cnetong.common.json.JsonUtil;
import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

/**
 * 结构化数据消息
 *
 * @author wangzc
 */
public class DefaultRecord implements Record {

    private final Schema schema;
    private final String charset;
    private final Map<String, RecordValue> data;

    @Builder
    public DefaultRecord(Schema schema, int initialCapacity, String charset) {
        this.schema = schema;
        this.charset = charset;
        this.data = new HashMap<>(initialCapacity);
    }

    public String asJson() {
        Map<String, Object> jsonObject = new HashMap<>();
        Map<String, Object> body = new HashMap<>();
        jsonObject.put("collection", getSchema().getName());
        jsonObject.put("body", body);
        for (Map.Entry<String, RecordValue> entry : data.entrySet()) {
            body.put(entry.getKey(), entry.getValue().asValue());
        }
        return JsonUtil.toJson(jsonObject);
    }


    @Override
    public void putColumn(String name, RecordValue column) {
        this.data.put(name, column);
    }

    @Override
    public String getCharset() {
        return charset;
    }

    public RecordValue getColumn(String name) {
        return this.data.get(name);
    }

    @Override
    public Map<String, RecordValue> getData() {
        return this.data;
    }

    @Override
    public Schema getSchema() {
        return schema;
    }
}
