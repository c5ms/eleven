package com.eleven.access.core.value;

import com.cnetong.access.core.RecordException;
import com.cnetong.access.core.RecordValue;
import org.apache.commons.lang3.time.FastDateFormat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Created by jingxing on 14-8-24.
 */

public class StringRecordValue extends AbstractRecordValue {


    private static final FastDateFormat dateFormatter = FastDateFormat.getInstance(DateRecordValue.dateFormat, DateRecordValue.timeZone);

    private static final FastDateFormat timeFormatter = FastDateFormat.getInstance(DateRecordValue.timeFormat, DateRecordValue.timeZone);

    private static final FastDateFormat datetimeFormatter = FastDateFormat.getInstance(DateRecordValue.datetimeFormat, DateRecordValue.timeZone);


    public StringRecordValue() {
        this(null);
    }

    public StringRecordValue(final String rawData) {
        super(rawData, RecordValue.Type.STRING, (null == rawData ? 0 : rawData.length()));
    }

    @Override
    public Object asValue() {
        return asString();
    }


    public String asString() {
        if (null == this.getRaw()) {
            return null;
        }
        return (String) this.getRaw();
    }


    private void validateDoubleSpecific(final String data) {
        if ("NaN".equals(data) || "Infinity".equals(data)
                || "-Infinity".equals(data)) {
            throw new RecordException(String.format("String[\"%s\"]属于Double特殊类型，不能转为其他类型 .", data));
        }
    }

    public BigInteger asBigInteger() {
        if (null == this.getRaw()) {
            return null;
        }

        this.validateDoubleSpecific((String) this.getRaw());

        try {
            return this.asBigDecimal().toBigInteger();
        } catch (Exception e) {
            throw new RecordException(String.format(
                    "String[\"%s\"]不能转为BigInteger .", this.asString()));
        }
    }

    @Override
    public Long asLong() {
        if (null == this.getRaw()) {
            return null;
        }

        this.validateDoubleSpecific((String) this.getRaw());

        try {
            BigInteger integer = this.asBigInteger();
            OverFlowUtil.validateLongNotOverFlow(integer);
            return integer.longValue();
        } catch (Exception e) {
            throw new RecordException(
                    String.format("String[\"%s\"]不能转为Long .", this.asString()));
        }
    }


    public BigDecimal asBigDecimal() {
        if (null == this.getRaw()) {
            return null;
        }

        this.validateDoubleSpecific((String) this.getRaw());

        try {
            return new BigDecimal(this.asString());
        } catch (Exception e) {
            throw new RecordException(String.format("String [\"%s\"] 不能转为BigDecimal .", this.asString()));
        }
    }

    @Override
    public Double asDouble() {
        if (null == this.getRaw()) {
            return null;
        }

        String data = (String) this.getRaw();
        if ("NaN".equals(data)) {
            return Double.NaN;
        }

        if ("Infinity".equals(data)) {
            return Double.POSITIVE_INFINITY;
        }

        if ("-Infinity".equals(data)) {
            return Double.NEGATIVE_INFINITY;
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

        if ("true".equalsIgnoreCase(this.asString())) {
            return true;
        }

        if ("false".equalsIgnoreCase(this.asString())) {
            return false;
        }


        if ("1".equalsIgnoreCase(this.asString())) {
            return true;
        }

        if ("0".equalsIgnoreCase(this.asString())) {
            return false;
        }

        throw new RecordException(
                String.format("String[\"%s\"]不能转为Bool .", this.asString()));
    }


    @Override
    public Date asDate() {
        try {
            if (null == asString()) {
                return null;
            }
            try {
                return datetimeFormatter.parse(asString());
            } catch (Exception ignored) {

            }
            try {
                return dateFormatter.parse(asString());
            } catch (Exception ignored) {

            }
            return timeFormatter.parse(asString());
        } catch (Exception e) {
            throw new RecordException(String.format("String[\"%s\"]不能转为Date .", this.asString()));
        }
    }


    @Override
    public byte[] asBytes() {
        try {
            String str = this.asString();
            if (null != str) {
                return str.getBytes(StandardCharsets.UTF_8);
            }
            return null;
        } catch (Exception e) {
            throw new RecordException(String.format("String[\"%s\"]不能转为Bytes .使用编码为:[UTF-8(系统默认)]", this.asString()));
        }
    }

}

