//package com.demcia.eleven.core.rest.download;
//
//import lombok.Data;
//import lombok.experimental.Accessors;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//@Data
//@Accessors(chain = true)
//public class DownloadFile {
//	private InputStream inputStream;
//	private Handler handler;
//	private String fileName;
//	private String contentType = "application/octet-stream";
//
//	public DownloadFile() {
//	}
//
//	public DownloadFile(InputStream inputStream) {
//		this.inputStream = inputStream;
//	}
//
//	public DownloadFile(Handler handler) {
//		this.handler = handler;
//	}
//
//
//	@FunctionalInterface
//	public interface Handler {
//		void write(OutputStream os) throws IOException;
//	}
//
//}
