package com.eleven.core.web.config;

import com.eleven.core.web.RestConstants;

import java.util.TimeZone;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "eleven.web")
public class ElevenWebProperties {

	private String dateFormat = RestConstants.DEFAULT_DATE_FORMAT;

	private String timeFormat = RestConstants.DEFAULT_DATE_FORMAT;

	private String datetimeFormat = RestConstants.DEFAULT_DATE_FORMAT + " " + RestConstants.DEFAULT_TIME_FORMAT;

	private TimeZone timeZone = TimeZone.getDefault();

}
