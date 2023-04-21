package com.eleven.core.exception;

public class ElevenRuntimeException extends RuntimeException {
  public ElevenRuntimeException() {
  }

  public ElevenRuntimeException(String message) {
    super(message);
  }

  public ElevenRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public ElevenRuntimeException(Throwable cause) {
    super(cause);
  }

  public ElevenRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
