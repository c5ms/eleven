package com.demcia.eleven;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.reactive.config.EnableWebFlux;


@EnableAsync
@EnableCaching
@SpringBootApplication
public class UpmsServiceApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(UpmsServiceApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}
