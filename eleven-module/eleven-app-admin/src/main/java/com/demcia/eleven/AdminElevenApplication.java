package com.demcia.eleven;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AdminElevenApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AdminElevenApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}
