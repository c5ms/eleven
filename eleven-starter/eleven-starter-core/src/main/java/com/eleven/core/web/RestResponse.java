package com.eleven.core.web;

import com.eleven.core.exception.ProcessError;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

public class RestResponse {

//    @Data
//    @Accessors(chain = true)
//    public static class Success<T> {
//        private String code = "0";
//        private String message;
//        private T data;
//
//        public static <T> Success<T> of(String message, T data) {
//            return new Success<T>()
//                    .setData(data)
//                    .setMessage(message);
//        }
//
//        public static <T> Success<T> of(T data) {
//            return new Success<T>()
//                    .setData(data);
//        }
//
//
//        public static <T> Success<T> of(String code, String message, T data) {
//            return new Success<T>()
//                    .setCode(code)
//                    .setMessage(message)
//                    .setData(data);
//        }
//
//    }

    @Data
    @Accessors(chain = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Failure {

        private String error;
        private String message;

        public Failure(String error, String message) {
            this.error = error;
            this.message = message;
        }

        public static Failure of(String error, String message) {
            return new Failure(error, message);
        }

        public static Failure of(ProcessError error) {
            return new Failure(error.getDomain() + ":" + error.getError(), error.getMessage());
        }
    }
}
