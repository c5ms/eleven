package com.eleven.access.core.value;

import com.cnetong.access.core.RecordException;

import java.util.Date;

/**
 * Created by jingxing on 14-8-24.
 */
public class BoolRecordValue extends AbstractRecordValue {

    public BoolRecordValue(Boolean bool) {
        super(bool, Type.BOOL, 1);
    }

    public BoolRecordValue(final String data) {
        this(true);

        if (null == data) {
            this.setRaw(null);
            this.setSize(0);
        } else {
            this.validate(data);
            this.setRaw("true".equalsIgnoreCase(data) || "1".equals(data));
            this.setSize(1);
        }
    }

    public BoolRecordValue() {
        super(null, Type.BOOL, 1);
    }


    private void validate(final String data) {
        if (null == data) {
            return;
        }

        if ("true".equalsIgnoreCase(data) || "false".equalsIgnoreCase(data)) {
            return;
        }

        if ("0".equalsIgnoreCase(data) || "1".equalsIgnoreCase(data)) {
            return;
        }

        throw new RecordException(String.format("String[%s]不能转为Bool .", data));
    }

    @Override
    public Long asLong() {
        if (null == this.getRaw()) {
            return null;
        }

        return this.asBoolean() ? 1L : 0L;
    }

    @Override
    public Double asDouble() {
        if (null == this.getRaw()) {
            return null;
        }

        return this.asBoolean() ? 1.0d : 0.0d;
    }

    @Override
    public String asString() {
        if (null == super.getRaw()) {
            return null;
        }
        return asBoolean().toString();
    }


    @Override
    public Date asDate() {
        return null;
    }

    @Override
    public byte[] asBytes() {
        return new byte[0];
    }

    @Override
    public Boolean asBoolean() {
        if (null == super.getRaw()) {
            return null;
        }

        return (Boolean) super.getRaw();
    }
}
