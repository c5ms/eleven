package com.eleven.access.core.value;

import com.cnetong.access.core.RecordException;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.TimeZone;

/**
 * Created by jingxing on 14-8-24.
 */
public class DateRecordValue extends AbstractRecordValue {

    static String datetimeFormat = "yyyy-MM-dd HH:mm:ss.SSS";

    static String dateFormat = "yyyy-MM-dd";

    static String timeFormat = "HH:mm:ss.SSS";

    static TimeZone timeZone = TimeZone.getTimeZone("GMT+0");

    private static DateType subType = DateType.DATETIME;

    /**
     * 构建值为null的DateColumn，使用Date子类型为DATETIME
     */
    public DateRecordValue() {
        this((Long) null);
    }

    /**
     * 构建值为stamp(Unix时间戳)的DateColumn，使用Date子类型为DATETIME
     * 实际存储有date改为long的ms，节省存储
     */
    public DateRecordValue(final Long stamp) {
        super(stamp, Type.DATE, (null == stamp ? 0 : 8));
    }

    /**
     * 构建值为date(java.util.Date)的DateColumn，使用Date子类型为DATETIME
     */
    public DateRecordValue(final Date date) {
        this(date == null ? null : date.getTime());
    }

    /**
     * 构建值为date(java.sql.Date)的DateColumn，使用Date子类型为DATE，只有日期，没有时间
     */
    public DateRecordValue(final java.sql.Date date) {
        this(date == null ? null : date.getTime());
        this.setSubType(DateType.DATE);
    }

    /**
     * 构建值为time(java.sql.Time)的DateColumn，使用Date子类型为TIME，只有时间，没有日期
     */
    public DateRecordValue(final java.sql.Time time) {
        this(time == null ? null : time.getTime());
        this.setSubType(DateType.TIME);
    }

    /**
     * 构建值为ts(java.sql.Timestamp)的DateColumn，使用Date子类型为DATETIME
     */
    public DateRecordValue(final java.sql.Timestamp ts) {
        this(ts == null ? null : ts.getTime());
        this.setSubType(DateType.DATETIME);
    }

    public DateType getSubType() {
        return subType;
    }

    public void setSubType(DateType subType) {
        DateRecordValue.subType = subType;
    }

    @Override
    public Object asValue() {
        return asString();
    }

    @Override
    public Long asLong() {
        return (Long) this.getRaw();
    }

    @Override
    public Double asDouble() {
        return Double.valueOf((Long) this.getRaw());
    }

    @Override
    public String asString() {
        if (null == this.asDate()) {
            return null;
        }
        switch (this.getSubType()) {
            case DATE:
                return DateFormatUtils.format(this.asDate(), dateFormat, timeZone);
            case TIME:
                return DateFormatUtils.format(this.asDate(), timeFormat, timeZone);
            case DATETIME:
                return DateFormatUtils.format(this.asDate(), datetimeFormat, timeZone);
            default:
                throw new RecordException("时间类型出现不支持类型，目前仅支持DATE/TIME/DATETIME.");
        }
    }

    @Override
    public Date asDate() {
        if (null == this.getRaw()) {
            return null;
        }

        return new Date((Long) this.getRaw());
    }

    @Override
    public byte[] asBytes() {
        return asString().getBytes();
    }

    @Override
    public Boolean asBoolean() {
        return null != this.getRaw();
    }

    public enum DateType {
        DATE, TIME, DATETIME
    }


}
