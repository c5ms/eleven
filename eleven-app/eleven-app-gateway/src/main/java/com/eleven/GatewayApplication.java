package com.eleven;

import cn.hutool.core.io.FileUtil;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(GatewayApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}
