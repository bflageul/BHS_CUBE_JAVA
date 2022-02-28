package com.cesi.bhs.api.data;

public class SimpleHttpResult {
  public int code;
  public String description;

  public SimpleHttpResult(int code, String description) {
    this.code = code;
    this.description = description;
  }
}
