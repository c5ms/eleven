package com.eleven.core.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class DownloadHttpMessageConverter extends AbstractHttpMessageConverter<Download> {

    public DownloadHttpMessageConverter() {
        super(MediaType.APPLICATION_OCTET_STREAM);
    }

    @Override
    protected boolean supports(@NonNull Class<?> clazz) {
        return clazz.isAssignableFrom(Download.class);
    }

    @Override
    @NonNull
    protected Download readInternal(@NonNull Class<? extends Download> clazz, @NonNull HttpInputMessage inputMessage) throws HttpMessageNotReadableException {
        return new Download();
    }

    @Override
    protected void writeInternal(@NonNull Download download, @NonNull HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        if (StringUtils.isNotBlank(download.getFilename())) {
            String fileName = URLEncoder.encode(download.getFilename(), StandardCharsets.UTF_8);
            outputMessage.getHeaders().add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        }

        if (StringUtils.isNotBlank(download.getContentType())) {
            outputMessage.getHeaders().setContentType(MediaType.valueOf(download.getContentType()));
        }

        if (null != download.getInputStream()) {
            try (InputStream is = download.getInputStream()) {
                IOUtils.copy(is, outputMessage.getBody());
            }
        }
        if (null != download.getHandler()) {
            download.getHandler().write(outputMessage.getBody());
        }
    }
}
