package com.cesi.bhs.api.db;

/**
 * An Employee user, they can edit the available stock and pass orders to suppliers.
 * Information about what they can do is taken from the job field.
 */
public interface Employee extends Users {
  public String getJob();

  public void setJob(String job);
}
