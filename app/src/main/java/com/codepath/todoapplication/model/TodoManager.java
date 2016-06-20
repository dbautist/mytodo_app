package com.codepath.todoapplication.model;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TodoManager {
  private static final String TAG = TodoManager.class.getSimpleName();
  private static TodoManager mInstance;
  private List<TodoItem> mItemList;
  private TodoItem mSelectedItem;

  private TodoManager() {
  }

  public static TodoManager getInstance() {
    if (mInstance == null) {
      mInstance = new TodoManager();
    }

    return mInstance;
  }

  public void init() {
    getItemList();
  }

  private void getItemList() {
    mItemList = new Select().from(TodoItem.class).execute();
  }

  public List<TodoItem> getItems() {
    if (mItemList == null)
      getItemList();

    return mItemList;
  }

  public boolean addItem(TodoItem item) {
    if (item != null) {
      item.save();
      getItemList();
      return true;
    } else {
      return false;
    }
  }

  public boolean removeItem(TodoItem item) {
    if (item != null) {
      item.delete();
      getItemList();
      return true;
    } else {
      return false;
    }
  }

  public TodoItem getSelectedItem() {
    return mSelectedItem;
  }

  public void setSelectedItem(TodoItem item) {
    this.mSelectedItem = item;
  }
}
