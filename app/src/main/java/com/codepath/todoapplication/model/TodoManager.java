package com.codepath.todoapplication.model;

import com.activeandroid.query.Select;

import java.util.List;

/**
 * This class manages the persistent state of TodoItem
 *
 */
public class TodoManager {
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

  /**
   * Add or update the given TodItem in the db.
   *
   * @param item
   */
  public void updateItem(TodoItem item) {
    if (item != null) {
      item.save();
      getItemList();
    }
  }

  /**
   * Delete the given TodoItem from the db.
   *
   * @param item
   */
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
