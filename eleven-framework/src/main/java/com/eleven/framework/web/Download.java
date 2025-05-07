package com.eleven.framework.web;

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

    public  static Download of(String filename,InputStream inputStream) {
        return new Download()
            .setFilename(filename)
            .setInputStream(inputStream);
    }

    public  static Download of(String filename,Handler handler) {
        return new Download()
            .setFilename(filename)
            .setHandler(handler);
    }

    @FunctionalInterface
    public interface Handler {
        void write(OutputStream os) throws IOException;
    }
}
