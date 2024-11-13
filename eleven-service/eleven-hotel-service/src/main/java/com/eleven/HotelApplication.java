package com.eleven;

import com.eleven.core.configure.EnableElevenDomain;
import io.hypersistence.utils.spring.repository.BaseJpaRepositoryImpl;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EnableElevenAuthentication
//@EnableElevenCache
@EnableElevenDomain
@EnableJpaRepositories
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class HotelApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(HotelApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}
