package org.example.mains;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

import static cn.hutool.http.HttpUtil.download;

@SpringBootApplication
public class MainsApplication {

    public static void main(String[] args) throws IOException {
//        SpringApplication.run(MainsApplication.class, args);

//        download();
        rename();
    }

    private static void download() {


    }

    private static void rename() throws IOException {
        AtomicInteger index= new AtomicInteger(1);
        Files.walk(Path.of("/Users/wangzc/Downloads/compressed"))
            .filter(Files::isRegularFile)
            .filter(path -> path.toString().endsWith(".mp3"))
            .sorted()
            .forEach(path -> {
                var fileName = path.getFileName().toString();
//                fileName=fileName.replace("IT人员的专业英语 2.0 - 通过远程工作、SDLC、Tech Stack、Scrum 等主题提升你的IT英语水平，提高你的软技能，促进职业发展 -","");
//                fileName=fileName.replace(")","");
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
//                fileName=fileName.replace("（","_");
//                fileName=fileName.replace(" No.","");
//                fileName=fileName.replace(" | 40篇英语短文搞定高考3500词沉浸式听力","");
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
                fileName=fileName.trim();
//                fileName= (index.getAndIncrement()) + " " + fileName;
                System.out.println(fileName);
//                FileUtil.rename(path.toFile(),path.getParent().resolve(fileName).toString(),true);
            });
    }

}
