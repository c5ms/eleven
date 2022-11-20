package com.eleven.access.core.value;

import com.cnetong.access.core.RecordException;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class LongRecordValue extends AbstractRecordValue {

    /**
     * 从整形字符串表示转为LongColumn，支持Java科学计数法
     * <p>
     * NOTE: <br>
     * 如果data为浮点类型的字符串表示，数据将会失真，请使用DoubleColumn对接浮点字符串
     */
    public LongRecordValue(final String data) {
        super(null, Type.LONG, 0);
        if (null == data) {
            return;
        }

        try {
            BigInteger rawData = NumberUtils.createBigDecimal(data).toBigInteger();
            super.setRaw(rawData);

            // 当 rawData 为[0-127]时，rawData.bitLength() < 8，导致其 byteSize = 0，简单起见，直接认为其长度为 data.length()
            // super.setByteSize(rawData.bitLength() / 8);
            super.setSize(data.length());
        } catch (Exception e) {
            throw new RecordException(String.format("String[%s]不能转为Long .", data));
        }
    }

    public LongRecordValue(Long data) {
        this(null == data ? null : BigInteger.valueOf(data));
    }

    public LongRecordValue(Integer data) {
        this(null == data ? null : BigInteger.valueOf(data));
    }

    public LongRecordValue(BigInteger data) {
        this(data, null == data ? 0 : 8);
    }

    private LongRecordValue(BigInteger data, int byteSize) {
        super(data, Type.LONG, byteSize);
    }

    public LongRecordValue() {
        this((BigInteger) null);
    }

    public BigInteger asBigInteger() {
        if (null == this.getRaw()) {
            return null;
        }

        return (BigInteger) this.getRaw();
    }

    @Override
    public Long asLong() {
        BigInteger rawData = (BigInteger) this.getRaw();
        if (null == rawData) {
            return null;
        }

        OverFlowUtil.validateLongNotOverFlow(rawData);

        return rawData.longValue();
    }

    @Override
    public Double asDouble() {
        if (null == this.getRaw()) {
            return null;
        }

        BigDecimal decimal = this.asBigDecimal();
        OverFlowUtil.validateDoubleNotOverFlow(decimal);

        return decimal.doubleValue();
    }


    @Override
    public Boolean asBoolean() {
        if (null == this.getRaw()) {
            return null;
        }

        return this.asBigInteger().compareTo(BigInteger.ZERO) != 0;
    }

    public BigDecimal asBigDecimal() {
        if (null == this.getRaw()) {
            return null;
        }
        return new BigDecimal(this.asBigInteger());
    }


    @Override
    public String asString() {
        if (null == this.getRaw()) {
            return null;
        }
        return this.asBigDecimal().toString();
    }


    @Override
    public Date asDate() {
        if (null == this.getRaw()) {
            return null;
        }
        return new Date(this.asLong());
    }

    @Override
    public byte[] asBytes() {
        throw new RecordException("Long类型不能转为Bytes .");
    }
}
