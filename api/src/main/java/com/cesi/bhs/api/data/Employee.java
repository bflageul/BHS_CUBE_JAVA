package com.cesi.bhs.api.data;

/**
 * An Employee user, they can edit the available stock and pass orders to suppliers.
 * Information about what they can do is taken from the job field.
 */
public interface Employee extends Users {
  String getJob();

  void setJob(String job);
}
