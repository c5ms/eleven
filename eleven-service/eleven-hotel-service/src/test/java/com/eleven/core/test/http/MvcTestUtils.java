package com.eleven.core.test.http;

import lombok.experimental.UtilityClass;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@UtilityClass
public class MvcTestUtils {

    public static MockHttpServletRequestBuilder doPost(String endpoint, String content) {
        return post(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
    }


    public static MockHttpServletRequestBuilder doPostJson(String endpoint, String filename) throws IOException {
        if (filename.startsWith("/")) {
            filename = filename.substring(1);
        }
        var file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + filename);
        var content = Files.readAllBytes(file.toPath());
        return doPost(endpoint, new String(content));
    }

}
