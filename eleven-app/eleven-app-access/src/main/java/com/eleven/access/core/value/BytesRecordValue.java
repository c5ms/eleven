package com.eleven.access.core.value;

import com.cnetong.access.core.RecordException;
import com.cnetong.access.core.RecordValue;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Date;

/**
 * Created by jingxing on 14-8-24.
 */
public class BytesRecordValue extends AbstractRecordValue {

    public BytesRecordValue() {
        this(null);
    }

    public BytesRecordValue(byte[] bytes) {
        super(ArrayUtils.clone(bytes), RecordValue.Type.BYTES, null == bytes ? 0 : bytes.length);
    }


    @Override
    public byte[] asBytes() {
        if (null == this.getRaw()) {
            return null;
        }
        return (byte[]) this.getRaw();
    }


    @Override
    public Long asLong() {
        throw new RecordException("Bytes类型不能转为Long .");
    }

    @Override
    public Double asDouble() {
        throw new RecordException("Bytes类型不能转为Double .");
    }

    @Override
    public String asString() {
        return new String(this.asBytes());
    }

    @Override
    public Date asDate() {
        throw new RecordException("Bytes类型不能转为Date .");
    }

    @Override
    public Boolean asBoolean() {
        throw new RecordException("Bytes类型不能转为Boolean .");
    }
}
