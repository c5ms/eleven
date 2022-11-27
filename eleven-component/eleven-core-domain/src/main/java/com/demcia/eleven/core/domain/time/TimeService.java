package com.demcia.eleven.core.domain.time;

import java.time.*;

/**
 * 当前时间
 */
public interface TimeService {

	/**
	 * 获取时区 ID
	 *
	 * @return 时区 ID
	 */
	ZoneId getZoneId();

	/**
	 * 获取当前时间戳
	 *
	 * @return 时间戳
	 */
	Instant getInstant();

	/**
	 * 读取本地日期
	 *
	 * @return 本地日期
	 */
	default LocalDate getLocalDate() {
		return LocalDate.ofInstant(this.getInstant(), this.getZoneId());
	}

	/***
	 * 读取本地时间
	 * @return 本地时间
	 */
	default LocalDateTime getLocalDateTime() {
		return LocalDateTime.ofInstant(this.getInstant(), this.getZoneId());
	}

	/***
	 * 读取本地时间
	 * @return 本地时间
	 */
	default LocalTime getLocalTime() {
		return LocalTime.ofInstant(this.getInstant(), this.getZoneId());
	}

}
