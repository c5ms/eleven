package com.eleven;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient(autoRegister = false)
@SpringBootApplication
public class ElevenApiApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ElevenApiApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}
