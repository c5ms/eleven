package com.eleven.access.core;

import com.cnetong.access.core.value.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@Getter
@Setter
public class SchemaField {
    private String name;
    private RecordValue.Type dataType;
    private boolean unique;
    private boolean nullable;

    public SchemaField(String name, RecordValue.Type dataType, boolean unique, boolean nullable) {
        this.name = name;
        this.dataType = dataType;
        this.unique = unique;
        this.nullable = nullable;
    }

    public void toUppercase() {
        this.name = this.name.toUpperCase(Locale.ROOT);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchemaField schemaField = (SchemaField) o;
        return Objects.equals(name, schemaField.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public RecordValue createValue(Number plainValue) {
        switch (dataType) {
            case BOOL:
                return new BoolRecordValue(plainValue.longValue() == 0);
            case LONG:
                return new LongRecordValue(plainValue.longValue());
            case DATE:
                return new DateRecordValue(plainValue.longValue());
            case DOUBLE:
                return new DoubleRecordValue(plainValue.doubleValue());
            case STRING:
                return new StringRecordValue(plainValue.toString());
        }
        return new NullRecordValue();
    }

    public RecordValue createValue(String plainValue) {
        switch (dataType) {
            case BOOL:
                return new BoolRecordValue(plainValue);
            case LONG:
                return new LongRecordValue(plainValue);
            case DOUBLE:
                return new DoubleRecordValue(plainValue);
            case STRING:
                return new StringRecordValue(plainValue);
        }
        return new NullRecordValue();
    }

    public RecordValue createValue(Serializable plainValue) {
        if (plainValue instanceof Date) {
            return new DateRecordValue((Date) plainValue);
        }
        if (plainValue instanceof byte[]) {
            return createValue(new String((byte[]) plainValue));
        }
        if (plainValue instanceof Long) {
            return createValue((Long) plainValue);
        }
        if (plainValue instanceof Integer) {
            return createValue((Integer) plainValue);
        }
        if (plainValue instanceof Short) {
            return createValue((Short) plainValue);
        }
        if (plainValue instanceof Byte) {
            return createValue((Byte) plainValue);
        }
        return new NullRecordValue();
    }
}
