package com.eleven.demo.hotel;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        var dir = "/Users/wangzc/Downloads";
        try (var files = Files.list(Paths.get(dir))) {
            files.filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".mp3"))
                .forEach(Main::rename);
        }
    }

    private static void rename(Path path) {
        var dir = path.getParent().toString();
        var name = path.toFile().getName();

        if (name.endsWith(".mp4.mp3")) {
            name = StringUtils.removeEnd(name, ".mp4.mp3");
        }
        name = StringUtils.substringAfterLast(name, "-");

        name = StringUtils.substringBefore(name, "✅");
        name = StringUtils.substringBefore(name, "|");
        name = StringUtils.trim(name);
        name = name + ".mp3";

        var filePath = Paths.get(dir, name);
        try {
            Files.move(path,filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
