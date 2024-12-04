package com.eleven.hotel.interfaces.utils;

import lombok.experimental.UtilityClass;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;


@UtilityClass
public class MvcTestUtils {

    public static MockHttpServletRequestBuilder withContent(MockHttpServletRequestBuilder requestBuilder, String filename) throws IOException {
        if (filename.startsWith("/")) {
            filename = filename.substring(1);
        }
        var file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + filename);
        var content = Files.readAllBytes(file.toPath());
        return requestBuilder
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
    }

    public static MultiValueMap<String, String> pageRequestParams(int page, int size) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", String.valueOf(page));
        params.add("size", String.valueOf(size));
        return params;
    }
}
