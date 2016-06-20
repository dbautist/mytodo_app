package com.codepath.todoapplication.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.UUID;

/*
    https://guides.codepath.com/android/activeandroid-guide
 */
@Table(name = "TodoItem")
public class TodoItem extends Model {
  @Column(name = "Title")
  public String title;

  @Column(name = "DateInMilliseconds")
  public long dateInMilliseconds;

  @Column(name = "Notes")
  public String notes;

  @Column(name = "Priority")
  public String priority;

  @Column(name = "Status")
  public String status;


  public TodoItem() {
    super();
  }
}
