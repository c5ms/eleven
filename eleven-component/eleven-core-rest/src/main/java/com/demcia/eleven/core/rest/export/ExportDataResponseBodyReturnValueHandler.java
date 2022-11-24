//package com.demcia.eleven.core.rest.export;
//
//import com.alibaba.excel.EasyExcel;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.HttpHeaders;
//import org.springframework.util.Assert;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.context.request.async.WebAsyncUtils;
//import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
//import org.springframework.web.method.support.ModelAndViewContainer;
//
//import javax.servlet.http.HttpServletResponse;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//
//public class ExportDataResponseBodyReturnValueHandler implements HandlerMethodReturnValueHandler {
//	@Override
//	public boolean supportsReturnType(MethodParameter returnType) {
//		return (ExportData.class.isAssignableFrom(returnType.getParameterType()));
//	}
//
//	@Override
//	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
//		if (null != returnValue) {
//			HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
//			Assert.state(response != null, "No HttpServletResponse");
//
//			@SuppressWarnings("unchecked")
//			ExportData<Object> exportData = (ExportData<Object>) returnValue;
//
//			String fileName = URLEncoder.encode(exportData.getTitle(), StandardCharsets.UTF_8) + ".xlsx";
//			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
//
//			WebAsyncUtils.getAsyncManager(webRequest).startCallableProcessing(() -> {
//				EasyExcel.write(response.getOutputStream(), exportData.getType())
//					.sheet(exportData.getTitle())
//					.useDefaultStyle(true)
//					.doWrite(exportData::getElements);
//				return null;
//			}, mavContainer);
//		}
//	}
//}
