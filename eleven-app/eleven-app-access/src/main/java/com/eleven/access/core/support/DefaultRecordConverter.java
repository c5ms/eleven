package com.eleven.access.core.support;

import com.cnetong.access.core.*;
import com.cnetong.access.core.value.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DefaultRecordConverter implements RecordConverter {
    private static final RecordValue NULL_COLUMN = new NullRecordValue();

    private static final SpelParserConfiguration spelParserConfiguration = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE, DefaultRecordConverter.class.getClassLoader());
    private static final ExpressionParser expressionParser = new SpelExpressionParser(spelParserConfiguration);

    private final Map<String, Expression> transExpression = new HashMap<>();
    private final Map<String, Mapping> mappingIndex = new HashMap<>();
    private final Map<String, MappingSchema> schemas = new HashMap<>();

    private final EvaluationContext evaluationContext;

    public DefaultRecordConverter(EvaluationContext evaluationContext) {
        this.evaluationContext = evaluationContext;
    }

    public void addMapping(String schema, String source, String target, String transform) {
        String key = schema + "::" + source;

        // 映射
        this.mappingIndex.put(key, new Mapping(schema, source, target, transform));

        // 转换表达式

        if (!transExpression.containsKey(transform) && StringUtils.isNoneBlank(transform)) {
            SpelExpression expression = (SpelExpression) expressionParser.parseExpression(transform);
            transExpression.put(transform, expression);
        }
    }


    @Override
    public Record convert(Record sourceRecord) {
        final Schema sourceSchema = sourceRecord.getSchema();
        if (null == sourceSchema) {
            return sourceRecord;
        }
        // 使用第一条数据分析schema
        // 数据类型无法改变
        if (!schemas.containsKey(sourceSchema.getName())) {
            MappingSchema schema = new MappingSchema(sourceSchema.getName());
            // 复制所有的列,并且标记目标字段
            for (SchemaField schemaField : sourceSchema.getFields()) {
                final String key = sourceSchema.getName() + "::" + schemaField.getName();
                final Mapping mapping = mappingIndex.get(key);
                if (null == mapping) {
                    final MappingSchemaField mappingColumn = new MappingSchemaField(schemaField.getName(), null, schemaField.isUnique(), schemaField);
                    schema.addMappingColumn(schemaField.getName(), mappingColumn);
                } else {
                    final String source = schemaField.getName();
                    final String target = mapping.getTarget();
                    if (StringUtils.isNotBlank(target)) {
                        final String transform = mapping.getTransform();
                        final MappingSchemaField mappingColumn = new MappingSchemaField(target, transform, schemaField.isUnique(), schemaField);
                        schema.addMappingColumn(source, mappingColumn);
                    }
                }
            }
            schemas.put(sourceSchema.getName(), schema);
        }

        final MappingSchema targetSchema = schemas.get(sourceSchema.getName());
        final Record targetRecord = new DefaultRecord(targetSchema, targetSchema.size(), sourceRecord.getCharset());

        // 遍历所有的列
        for (SchemaField schemaField : sourceRecord.getSchema().getFields()) {

            // 原列
            RecordValue sourceColumn = sourceRecord.getColumn(schemaField.getName());

            // 目标字段
            MappingSchemaField mappingColumn = targetSchema.getTarget(schemaField.getName());

            //  没有映射目标
            if (null == mappingColumn) {
                continue;
            }

            final Object source = sourceColumn.getRaw();
            final String transform = mappingColumn.getTransform();

            // 配置了转换
            if (StringUtils.isNotBlank(transform)) {
                targetRecord.putColumn(mappingColumn.getName(), transform(source, transform));
            }
            // 没有配置转换
            else {
                targetRecord.putColumn(mappingColumn.getName(), sourceColumn);
            }
        }
        return targetRecord;
    }


    /**
     * 字段转换
     *
     * @param source    来源
     * @param transform 转换逻辑
     * @return 如果不村
     */
    private RecordValue transform(Object source, String transform) {

        RecordValue targetColumn = NULL_COLUMN;

        // 尝试spring 表达式转换
        if (null != transExpression.get(transform)) {
            Object object = transExpression.get(transform).getValue(evaluationContext, source);
            if (object instanceof String) {
                targetColumn = new StringRecordValue((String) object);

            } else if (object instanceof Float) {
                targetColumn = new DoubleRecordValue((Float) object);

            } else if (object instanceof Double) {
                targetColumn = new DoubleRecordValue((Double) object);

            } else if (object instanceof Integer) {
                targetColumn = new DoubleRecordValue((Integer) object);

            } else if (object instanceof Long) {
                targetColumn = new DoubleRecordValue((Long) object);

            } else if (object instanceof Date) {
                targetColumn = new DateRecordValue((Date) object);

            } else if (object instanceof Boolean) {
                targetColumn = new BoolRecordValue((Boolean) object);

            } else if (object instanceof byte[]) {
                targetColumn = new BytesRecordValue((byte[]) object);
            }
        }
        return targetColumn;
    }


    public static class MappingSchema extends Schema {
        private final Map<String, MappingSchemaField> mappingIndex = new HashMap<>();

        public MappingSchema(String collection) {
            super(collection);
        }

        /**
         * 添加一个映射
         *
         * @param source 原字段
         * @param column 目标列
         */
        public void addMappingColumn(String source, MappingSchemaField column) {
            mappingIndex.put(source, column);
            super.addField(column);
        }


        /**
         * 数据长度(多少列)
         *
         * @return 长度
         */
        public int size() {
            return this.mappingIndex.size();
        }

        /**
         * 获取目标列
         *
         * @param source 原字段
         * @return 目标列
         */
        public MappingSchemaField getTarget(String source) {
            return this.mappingIndex.get(source);
        }
    }

    @Getter
    @Setter
    public static class MappingSchemaField extends SchemaField {
        private final String transform;

        /**
         * @param target      目标列
         * @param transform   转换处理
         * @param schemaField 原列类型
         */
        public MappingSchemaField(String target, String transform, boolean unque, SchemaField schemaField) {
            super(target, schemaField.getDataType(), unque, schemaField.isNullable());
            this.transform = transform;
        }
    }

    @lombok.Value
    public static class Mapping {
        String collection;
        String source;
        String target;
        String transform;
    }


}
