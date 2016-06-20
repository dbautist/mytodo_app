package com.codepath.todoapplication.model;

import com.activeandroid.query.Select;

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

  /**
   * Return the list of to-dos sorted by date
   */
  private void getItemList() {
    mItemList = new Select().from(TodoItem.class).orderBy("DateInMilliseconds").execute();
  }

  public List<TodoItem> getItems() {
    if (mItemList == null)
      getItemList();

    return mItemList;
  }

  public void updateItem(TodoItem item) {
    if (item != null) {
      item.save();
      getItemList();
    }
  }

  public void removeItem(TodoItem item) {
    if (item != null) {
      item.delete();
      getItemList();
    }
  }

  public TodoItem getSelectedItem() {
    return mSelectedItem;
  }

  public void setSelectedItem(TodoItem item) {
    this.mSelectedItem = item;
  }
}
