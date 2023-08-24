package com.eleven.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class CopyUtil {

    public static CopyObject notBlank(Supplier<String> supplier) {
        return new CopyObject().notBlank(supplier);
    }

    public static CopyObject notNull(Supplier<Object> supplier) {
        return new CopyObject().notNull(supplier);
    }

    @FunctionalInterface
    public interface BigDecimalConsumer extends Consumer<BigDecimal> {

    }

    public static class CopyObject {
        private Object value;
        private boolean match;

        public CopyObject notBlank(Supplier<String> supplier) {
            var value = supplier.get();
            this.match = StringUtils.isNotBlank(value);
            this.value = value;
            return this;
        }

        public CopyObject notNull(Supplier<Object> supplier) {
            var value = supplier.get();
            this.match = Objects.nonNull(value);
            this.value = value;
            return this;
        }

        public CopyObject trim(Consumer<String> consumer) {
            if (this.match) {
                consumer.accept(StringUtils.trim((String) this.value));
            }
            return this;
        }

        @SuppressWarnings("unchecked")
        public <T> CopyObject set(Consumer<T> consumer) {
            if (this.match) {
                consumer.accept((T) this.value);
            }
            return this;
        }

        public CopyObject set(BigDecimalConsumer consumer) {
            if (this.match) {
                consumer.accept((BigDecimal) this.value);
            }
            return this;
        }
    }
}
