package com.eleven;

import com.eleven.core.configure.EnableElevenCore;
import com.eleven.core.configure.EnableElevenDomain;
import com.eleven.core.configure.EnableElevenRest;
import com.eleven.core.configure.EnableElevenSecurity;
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
