package com.cesi.bhs.api.db;

public class Employee extends Users{
  private String job;

  public String getJob() {
    return job;
  }

  public void setJob(String job) {
    this.job = job;
  }
}
