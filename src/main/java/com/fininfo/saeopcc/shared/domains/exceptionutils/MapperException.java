package com.fininfo.saeopcc.shared.domains.exceptionutils;

public class MapperException extends RuntimeException {
  public MapperException(String msg, Throwable t) {
    super(msg, t);
  }

  public MapperException(String msg) {
    super(msg);
  }
}
