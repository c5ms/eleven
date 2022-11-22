package com.demcia.eleven.core.rest.bootstrap;

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

    public static void log(String info) {
        synchronized (System.out) {
            infos.add(getFormat(info));
        }
    }

    private static String getFormat(String info) {
        return String.format("【" + SpringUtil.getApplicationName() + "】 %s ", info);
    }

    public static void flush() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                log.info(getFormat("程序准备就绪"));
                Bootstrap.infos.forEach(log::info);
            }
        }, TimeUnit.SECONDS.toMillis(1));
    }
}
