package com.eleven.core.web.config;

import com.eleven.core.web.WebConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.TimeZone;

@Data
@ConfigurationProperties(prefix = "eleven.web")
public class ElevenWebProperties {

    private String dateFormat = WebConstants.DEFAULT_DATE_FORMAT;

    private String timeFormat = WebConstants.DEFAULT_DATE_FORMAT;

    private String datetimeFormat = WebConstants.DEFAULT_DATE_FORMAT + " " + WebConstants.DEFAULT_TIME_FORMAT;

    private TimeZone timeZone = TimeZone.getDefault();

}
