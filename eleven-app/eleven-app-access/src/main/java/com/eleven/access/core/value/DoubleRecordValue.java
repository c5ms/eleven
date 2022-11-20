package com.eleven.access.core.value;

import com.cnetong.access.core.RecordException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class DoubleRecordValue extends AbstractRecordValue {

    public DoubleRecordValue(final String data) {
        this(data, null == data ? 0 : data.length());
        this.validate(data);
    }

    public DoubleRecordValue(Long data) {
        this(data == null ? null : String.valueOf(data));
    }

    public DoubleRecordValue(Integer data) {
        this(data == null ? null : String.valueOf(data));
    }

    /**
     * Double无法表示准确的小数数据，我们不推荐使用该方法保存Double数据，建议使用String作为构造入参
     */
    public DoubleRecordValue(final Double data) {
        this(data == null ? null : new BigDecimal(String.valueOf(data)).toPlainString());
    }

    /**
     * Float无法表示准确的小数数据，我们不推荐使用该方法保存Float数据，建议使用String作为构造入参
     */
    public DoubleRecordValue(final Float data) {
        this(data == null ? null : new BigDecimal(String.valueOf(data)).toPlainString());
    }

    public DoubleRecordValue(final BigDecimal data) {
        this(null == data ? null : data.toPlainString());
    }

    public DoubleRecordValue(final BigInteger data) {
        this(null == data ? null : data.toString());
    }

    public DoubleRecordValue() {
        this((String) null);
    }

    private DoubleRecordValue(final String data, int byteSize) {
        super(data, Type.DOUBLE, byteSize);
    }


    public BigDecimal asBigDecimal() {
        if (null == this.getRaw()) {
            return null;
        }

        try {
            return new BigDecimal((String) this.getRaw());
        } catch (NumberFormatException e) {
            throw new RecordException(String.format("String[%s] 无法转换为BigDecimal类型 .", this.getRaw()));
        }
    }


    @Override
    public Double asDouble() {
        if (null == this.getRaw()) {
            return null;
        }

        String string = (String) this.getRaw();

        boolean isDoubleSpecific = string.equals("NaN") || string.equals("-Infinity") || string.equals("+Infinity");
        if (isDoubleSpecific) {
            return Double.valueOf(string);
        }

        BigDecimal result = this.asBigDecimal();
        OverFlowUtil.validateDoubleNotOverFlow(result);

        return result.doubleValue();
    }


    @Override
    public String asString() {
        if (null == this.getRaw()) {
            return null;
        }
        return (String) this.getRaw();
    }

    @Override
    public Long asLong() {
        if (null == this.getRaw()) {
            return null;
        }

        BigDecimal result = this.asBigDecimal();
        OverFlowUtil.validateLongNotOverFlow(result.toBigInteger());

        return result.longValue();
    }


    @Override
    public Boolean asBoolean() {
        throw new RecordException("Double类型无法转为Bool .");
    }

    @Override
    public Date asDate() {
        throw new RecordException("Double类型无法转为Date类型 .");
    }

    @Override
    public byte[] asBytes() {
        return this.asString().getBytes();
    }

    private void validate(final String data) {
        if (null == data) {
            return;
        }

        if (data.equalsIgnoreCase("NaN") || data.equalsIgnoreCase("-Infinity")
                || data.equalsIgnoreCase("Infinity")) {
            return;
        }

        try {
            new BigDecimal(data);
        } catch (Exception e) {
            throw new RecordException(String.format("String[%s]无法转为Double类型 .", data));
        }
    }
}
