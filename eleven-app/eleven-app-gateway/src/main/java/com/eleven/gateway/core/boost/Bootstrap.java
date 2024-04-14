package com.eleven.gateway.core.boost;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Bootstrap {
	static List<String> infos = new ArrayList<>();
	static List<String> errors = new ArrayList<>();

	public static void log(String info) {
			infos.add(getFormat(info));
	}

	public static void error(String info) {
			errors.add(getFormat(info));
	}

	private static String getFormat(String info) {
		return String.format("【" + SpringUtil.getApplicationName() + "】 %s ", info);
	}

	public static void flush() {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {

				Bootstrap.infos.forEach(log::info);
				Bootstrap.errors.forEach(log::error);
			}
		}, TimeUnit.SECONDS.toMillis(1));
	}
}
