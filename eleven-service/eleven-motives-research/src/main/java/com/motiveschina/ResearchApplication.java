package com.motiveschina;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ResearchApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ResearchApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}
