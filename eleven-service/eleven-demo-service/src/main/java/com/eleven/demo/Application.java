package com.eleven.demo;

import com.eleven.framework.configure.EnableElevenCore;
import com.eleven.framework.configure.EnableElevenDomain;
import com.eleven.framework.configure.EnableElevenRest;
import com.eleven.framework.configure.EnableElevenSecurity;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableElevenCore
@EnableElevenDomain
@EnableElevenRest
@EnableElevenSecurity
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}
