package com.eleven.core.exception;

public class ElevenException extends Exception{
  public ElevenException() {
  }

  public ElevenException(String message) {
    super(message);
  }

  public ElevenException(String message, Throwable cause) {
    super(message, cause);
  }

  public ElevenException(Throwable cause) {
    super(cause);
  }

  public ElevenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
