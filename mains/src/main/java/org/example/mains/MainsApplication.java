package org.example.mains;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class MainsApplication {

    public static void main(String[] args) throws IOException {
//        SpringApplication.run(MainsApplication.class, args);

//        redressContent();
        rename();
    }

    private static void redressContent() throws IOException {
        Files.walk(Path.of("/Users/wangzc/Downloads"))
            .filter(Files::isRegularFile)
//            .filter(path -> path.endsWith("Journey to the West 1- The Monkey.txt"))
            .forEach(path -> {
                var file = path.toFile();
                var content = FileUtil.readString(file, StandardCharsets.UTF_8);
                var title = FileUtil.mainName(file);
                FileUtil.writeString(title, file, StandardCharsets.UTF_8);
                FileUtil.appendString("\n", file, StandardCharsets.UTF_8);
                FileUtil.appendString(content, file, StandardCharsets.UTF_8);

                var fileName = path.getFileName().toString();
                var index= Integer.parseInt(StringUtils.substring(fileName.split(" ")[4],0,-1));
                fileName = StringUtils.substring(fileName, fileName.indexOf("-") + 1, fileName.length());
                fileName = String.format("Journey to the West %03d %s", index, fileName.trim());
               FileUtil.rename(path.toFile(), path.getParent().resolve(fileName).toString(), true);
            });

    }

    private static void rename() throws IOException {
        AtomicInteger index = new AtomicInteger(1);
        Files.walk(Path.of("/Users/wangzc/Downloads/1"), FileVisitOption.FOLLOW_LINKS)
            .filter(Files::isRegularFile)
            .filter(path -> path.toString().endsWith(".mp3"))
            .forEach(path -> {
                var fileName = path.getFileName().toString();
//                fileName=fileName.replace("IT人员的专业英语 2.0 - 通过远程工作、SDLC、Tech Stack、Scrum 等主题提升你的IT英语水平，提高你的软技能，促进职业发展 -","");
//                fileName=fileName.replace(")","");
                fileName = StringUtils.substring(fileName, fileName.lastIndexOf("  ") + 1);
//                fileName = StringUtils.substring(fileName, fileName.lastIndexOf("-") + 1);
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
                fileName = fileName.replaceAll("[^a-zA-Z0-9-./]", " ");
//                fileName = fileName.replaceAll("\\s+", " ");
//                fileName = fileName.trim();
                fileName =StringUtils.substringBefore(fileName,".mp3");
                fileName = fileName.trim() + ".mp3";
//                fileName = fileName.replace(".mp3-0.mp3", "-1.mp3");
//                fileName = fileName.replace(".mp3-1.mp3", "-2.mp3");
//                fileName = fileName.replace(".mp3.mp3", ".mp3");
//                fileName = fileName.replace(".mp3.mp3", ".mp3");
//                fileName = fileName.replace(" .mp3", ".mp3");
//                fileName = fileName.replace(".mp4.mp3", ".mp3");
//                fileName = fileName.replace("….", ").");
                fileName = fileName.trim();
//                fileName= (index.getAndIncrement()) + " " + fileName;
                fileName=    fileName.replace(".txt",".lrc");
                System.out.println(fileName);
                FileUtil.rename(path.toFile(), path.getParent().resolve(fileName).toString(), true);
            })
        ;
    }

}
