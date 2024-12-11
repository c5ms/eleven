package com.eleven.core.web;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Data
@Accessors(chain = true)
public class Download {
    private String filename;
    private InputStream inputStream;
    private Handler handler;
    private String contentType = "application/octet-stream";

    public Download() {
    }

    public Download(String filename) {
        this.filename = filename;
    }

    @FunctionalInterface
    public interface Handler {
        void write(OutputStream os) throws IOException;
    }
}
