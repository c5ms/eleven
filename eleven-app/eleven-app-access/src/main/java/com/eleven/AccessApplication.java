package com.eleven;

import cn.hutool.core.io.FileUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class AccessApplication {

    public static void main(String[] args) throws IOException {
//        SpringApplication application = new SpringApplication(DemoApplication.class);
//        application.setBannerMode(Banner.Mode.OFF);
//        application.run(args);

        Files.walk(Path.of("/Users/wangzc/Downloads/核心高频单词3600"))
            .filter(Files::isRegularFile)
            .filter(path -> path.toString().endsWith(".mp3"))
            .sorted()
            .forEach(path -> {
                var fileName = path.getFileName().toString();
//                fileName = StringUtils.substring(fileName, fileName.lastIndexOf("-")+1);
//                fileName=    RegExUtils.replaceFirst(fileName,"\\."," -");
//                fileName = StringUtils.substringAfter(fileName, "【YouTube最好的英语训练课程】");
//                fileName = StringUtils.substringAfter(fileName, "-");
//                fileName = StringUtils.substringAfter(fileName, "-");
//                fileName=fileName.replace("高效提升英语听力","高效提升英语听力-");
//                fileName=fileName.replace("高效练耳朵英语听力_-","高效提升英语听力-");
//                fileName=fileName.replace("英语听力高效练习","高效提升英语听力-");
//                fileName=fileName.replace("高效提升英语听力-_","");
//                fileName=fileName.replace("Day ","Day_");
//                fileName=fileName.replace("_-_","-");

//                fileName=fileName.replace("（美式英语发音 + 课文跟读字…","");
//fileName=fileName.replace(" 听力精听泛听练习 "," ");
//                fileName = StringUtils.substringBefore(fileName, "|");
//                fileName = StringUtils.substringBefore(fileName, "--");
//                fileName = fileName.replaceAll("[^a-zA-Z0-9-./]", " ");
//                fileName = fileName.replaceAll("\\s+", " ");
//                fileName = fileName.trim();
//                fileName =StringUtils.substringBefore(fileName,".mp3");
//                fileName = fileName + ".mp3";
//                fileName = fileName.replace(".mp3-0.mp3", "-1.mp3");
//                fileName = fileName.replace(".mp3-1.mp3", "-2.mp3");
//                fileName = fileName.replace(".mp3.mp3", ".mp3");
                fileName = fileName.replace(".mp3.mp3", ".mp3");
                fileName = fileName.replace(" .mp3", ".mp3");
                fileName = fileName.replace("….", ").");

                System.out.println(fileName);
                FileUtil.rename(path.toFile(),path.getParent().resolve(fileName).toString(),true);
            });
    }

}
