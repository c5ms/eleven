//package com.demcia.eleven.core.rest.download;
//
//import cn.hutool.core.io.IoUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.HttpHeaders;
//import org.springframework.util.Assert;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
//import org.springframework.web.method.support.ModelAndViewContainer;
//
//import java.io.InputStream;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//
//public class DownloadFileResponseBodyReturnValueHandler implements HandlerMethodReturnValueHandler {
//    @Override
//    public boolean supportsReturnType(MethodParameter returnType) {
//        return DownloadFile.class.isAssignableFrom(returnType.getParameterType());
//    }
//
//    @Override
//    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
//        if (null != returnValue) {
//            HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
//            Assert.state(response != null, "No HttpServletResponse");
//            DownloadFile downloadFile = (DownloadFile) returnValue;
//
//            if (StringUtils.isNotBlank(downloadFile.getFileName())) {
//                String fileName = URLEncoder.encode(downloadFile.getFileName(), StandardCharsets.UTF_8);
//                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
//            }
//
//            if (StringUtils.isNotBlank(downloadFile.getContentType())) {
//                response.setContentType(downloadFile.getContentType());
//            }
//            if (null != downloadFile.getInputStream()) {
//                try (InputStream is = downloadFile.getInputStream()) {
//                    IoUtil.copy(is, response.getOutputStream());
//                    response.getOutputStream().close();
//                }
//            }
//            if (null != downloadFile.getHandler()) {
//                downloadFile.getHandler().write(response.getOutputStream());
//                response.getOutputStream().close();
//            }
//        }
//    }
//
//}