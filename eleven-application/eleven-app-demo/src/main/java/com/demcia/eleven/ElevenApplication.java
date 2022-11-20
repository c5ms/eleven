package com.demcia.eleven;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableFeignClients
@SpringBootApplication
public class ElevenApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ElevenApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}
