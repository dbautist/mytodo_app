package com.codepath.todoapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.UUID;

/*
    Reference: https://guides.codepath.com/android/activeandroid-guide#populating-listview-with-arrayadapter
 */
@Table(name = "TodoItem")
public class TodoItem extends Model implements Parcelable {
  @Column(name = "GUID", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
  public String guid;

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
    guid = UUID.randomUUID().toString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.guid);
    dest.writeString(this.title);
    dest.writeLong(this.dateInMilliseconds);
    dest.writeString(this.notes);
    dest.writeString(this.priority);
    dest.writeString(this.status);
    dest.writeLong(super.getId());
  }

  protected TodoItem(Parcel in) {
    this.guid = in.readString();
    this.title = in.readString();
    this.dateInMilliseconds = in.readLong();
    this.notes = in.readString();
    this.priority = in.readString();
    this.status = in.readString();
  }

  public static final Creator<TodoItem> CREATOR = new Creator<TodoItem>() {
    @Override
    public TodoItem createFromParcel(Parcel source) {
      return new TodoItem(source);
    }

    @Override
    public TodoItem[] newArray(int size) {
      return new TodoItem[size];
    }
  };
}
