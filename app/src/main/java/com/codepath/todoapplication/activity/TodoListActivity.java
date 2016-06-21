package com.codepath.todoapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.todoapplication.R;
import com.codepath.todoapplication.adapter.TodoAdapter;
import com.codepath.todoapplication.model.TodoItem;
import com.codepath.todoapplication.model.TodoManager;

import java.util.List;

public class TodoListActivity extends AppCompatActivity {

  private static final String TAG = "LIST";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);

    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setIcon(R.mipmap.ic_launcher);
  }

  @Override
  protected void onResume() {
    super.onResume();

    List<TodoItem> todoItemList = TodoManager.getInstance().getItems();
    final TodoAdapter adapter = new TodoAdapter(this, todoItemList);
    ListView listView = (ListView) findViewById(R.id.todoListView);
    listView.setAdapter(adapter);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TodoItem item = adapter.getItem(position);
        Log.d(TAG, "Item clicked at position: " + position + " , edit: " + item.title);

        TodoManager.getInstance().setSelectedItem(item);
        gotoItemDetails();
      }
    });
    TodoManager.getInstance().setSelectedItem(null);
  }

  public void onAddItem(View view) {
    Intent intent = new Intent(this, TodoInputActivity.class);
    startActivity(intent);
  }

  public void gotoItemDetails() {
    Intent intent = new Intent(this, TodoDetailsActivity.class);
    startActivity(intent);
  }
}
