package com.eleven;

import com.google.common.collect.Range;
import io.hypersistence.utils.spring.repository.BaseJpaRepositoryImpl;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;
import java.time.ZonedDateTime;

//@EnableElevenAuthentication
//@EnableElevenCache
@EnableJpaRepositories(repositoryBaseClass = BaseJpaRepositoryImpl.class)
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
