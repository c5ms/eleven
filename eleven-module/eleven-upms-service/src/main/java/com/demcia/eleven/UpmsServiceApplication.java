package com.demcia.eleven;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class UpmsServiceApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(UpmsServiceApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}
