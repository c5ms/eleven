package com.eleven;

import cn.hutool.core.util.IdUtil;
import com.eleven.core.domain.support.RaindropGenerator;
import com.eleven.core.domain.support.SnowflakeIdentityGenerator;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class HotelApplication {

    public static void main(String[] args) {
        System.out.println(new RaindropGenerator().next());;
        System.out.println(IdUtil.fastSimpleUUID());
        System.out.println(IdUtil.nanoId());
        System.out.println(IdUtil.getSnowflakeNextId());
//        SpringApplication application = new SpringApplication(HotelApplication.class);
//        application.setBannerMode(Banner.Mode.OFF);
//        application.run(args);
    }

}
