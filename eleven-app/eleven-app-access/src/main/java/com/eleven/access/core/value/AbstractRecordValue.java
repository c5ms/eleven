package com.eleven.access.core.value;


import com.cnetong.access.core.RecordValue;
import lombok.Getter;
import lombok.Setter;


/**
 * 抽象的数据记录列实现
 */
@Getter
@Setter
public abstract class AbstractRecordValue implements RecordValue {

    private Type type;
    private Object raw;
    private int size;

    public AbstractRecordValue(final Object raw, final Type type, int size) {
        this.raw = raw;
        this.type = type;
        this.size = size;
    }

    @Override
    public Object asValue() {
        return getRaw();
    }

}
