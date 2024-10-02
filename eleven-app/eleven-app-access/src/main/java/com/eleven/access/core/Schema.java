package com.eleven.access.core;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 结构化数据元信息
 */
public class Schema {
    /**
     * 当前结构化数据的数据集合名称
     */
    private String name;

    /**
     * 数据字段列表
     */
    private final List<SchemaField> fields;

    public Schema(String name) {
        this.name = name;
        this.fields = new ArrayList<>();
    }


    /**
     * 添加字段列
     *
     * @param schemaField 字段列
     */
    public void addField(SchemaField schemaField) {
        fields.add(schemaField);
    }

    /**
     * 获取唯一值的列
     *
     * @return 唯一值的列
     */
    public List<SchemaField> getUniqueFields() {
        return fields.stream()
                .filter(SchemaField::isUnique)
                .collect(Collectors.toList());
    }

    /**
     * 获取所有的列,但是不允许修改
     *
     * @return 列集合
     */
    public Collection<SchemaField> getFields() {
        return this.fields;
    }

    /**
     * 找到对应字段，
     *
     * @param name 字段名
     * @return 字段
     */
    public Optional<SchemaField> getField(String name) {
        return fields.stream()
                .filter(schemeField -> schemeField.getName().equals(name))
                .findFirst();
    }

    /**
     * 检查是否包含某列
     *
     * @param name 列名
     * @return 是否包含
     */
    public boolean hasField(String name) {
        for (SchemaField field : fields) {
            if (field.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取数据集合名称
     *
     * @return 数据集合名称
     */
    public String getName() {
        return name;
    }

    /**
     * 按照序列化的内容，创建一条记录
     *
     * @param data 记录
     * @return 记录
     */
    public DefaultRecord createRecord(Serializable[] data) {
        var idx = 0;
        var record = new DefaultRecord(this, data.length, StandardCharsets.UTF_8.name());
        for (SchemaField schemaField : getFields()) {
            var columnName = schemaField.getName();
            var plainValue = data[idx];
            var recordValue = schemaField.createValue(plainValue);
            record.putColumn(columnName, recordValue);
            idx++;
        }
        return record;
    }


    public void tpUppercase() {
        this.name = this.name.toUpperCase(Locale.ROOT);
        for (SchemaField field : this.fields) {
            field.toUppercase();
        }
    }
}
