package com.eleven.access.core.value;

import java.util.Date;

public class NullRecordValue extends AbstractRecordValue {
    public NullRecordValue() {
        super(null, Type.NULL, 0);
    }

    @Override
    public Long asLong() {
        return null;
    }

    @Override
    public Double asDouble() {
        return null;
    }

    @Override
    public String asString() {
        return null;
    }

    @Override
    public Boolean asBoolean() {
        return null;
    }

    @Override
    public byte[] asBytes() {
        return new byte[0];
    }

    @Override
    public Date asDate() {
        return null;
    }
}
