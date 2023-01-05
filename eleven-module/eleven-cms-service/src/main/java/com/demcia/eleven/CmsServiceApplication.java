package com.demcia.eleven;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CmsServiceApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(CmsServiceApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}
