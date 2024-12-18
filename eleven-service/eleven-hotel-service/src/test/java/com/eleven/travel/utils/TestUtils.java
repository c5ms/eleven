package com.eleven.travel.utils;

import lombok.experimental.UtilityClass;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;


@UtilityClass
public class TestUtils {

    public static String loadJson(String filename) {
        try {
            if (filename.startsWith("/")) {
                filename = filename.substring(1);
            }
            var file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + filename);
            var content = Files.readAllBytes(file.toPath());
            return new String(content);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static MultiValueMap<String, String> pageRequestParams(int page, int size) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", String.valueOf(page));
        params.add("size", String.valueOf(size));
        return params;
    }
}
