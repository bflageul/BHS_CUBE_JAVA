package com.cesi.bhs.api.db;

public class EmployeeImpl extends UsersImpl {
  private String job;

  public String getJob() {
    return job;
  }

  public void setJob(String job) {
    this.job = job;
  }
}
