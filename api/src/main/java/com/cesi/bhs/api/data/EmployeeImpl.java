package com.cesi.bhs.api.data;

public class EmployeeImpl extends UsersImpl implements Employee {
  private String job;

  @Override
  public String getJob() {
    return job;
  }

  @Override
  public void setJob(String job) {
    this.job = job;
  }
}
