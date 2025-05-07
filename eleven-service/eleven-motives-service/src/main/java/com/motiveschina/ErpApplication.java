package com.motiveschina;

import com.eleven.framework.configure.EnableElevenFramework;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableElevenFramework
@EnableDiscoveryClient
@EnableJpaAuditing
@EnableFeignClients
@EnableScheduling
@SpringBootApplication
@ComponentScan("com.motiveschina.erp")
public class ErpApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ErpApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}
