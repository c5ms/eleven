package com.eleven.core.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.TimeZone;

@Data
@ConfigurationProperties(prefix = "eleven.core")
public class ElevenCoreProperties {

    private final Json json = new Json();

    @Data
    public static class Json {
        private String yearMonthFormat = "yyyy-MM";

        private String dateFormat = "yyyy-MM-dd";

        private String timeFormat = "HH:mm:ss";

        private String datetimeFormat = dateFormat + " " + timeFormat;

        private TimeZone timeZone = TimeZone.getDefault();
    }
}
