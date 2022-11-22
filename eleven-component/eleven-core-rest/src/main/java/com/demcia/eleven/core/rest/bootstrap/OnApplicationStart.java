package com.demcia.eleven.core.rest.bootstrap;

import cn.hutool.core.net.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OnApplicationStart {

	@EventListener(ApplicationStartedEvent.class)
	public void onApplicationStarted(ApplicationStartedEvent e) {
		Environment env = e.getApplicationContext().getEnvironment();
		String ip = NetUtil.getLocalhostStr();
		String port = env.getProperty("server.port", "80");
		String path = env.getProperty("server.servlet.context-path", "");
		Bootstrap.log("本地访问网址: http://localhost:" + port + path);
		Bootstrap.log("外部访问网址: http://" + ip + ":" + port + path);
		Bootstrap.flush();
	}

}
