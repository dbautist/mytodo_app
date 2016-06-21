package com.codepath.todoapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codepath.todoapplication.model.TodoManager;

/*
  https://www.bignerdranch.com/blog/splash-screens-the-right-way/
 */
public class SplashScreenActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Fetch our list of to-do from the DB
    TodoManager.getInstance().init();

    Intent intent = new Intent(this, TodoListActivity.class);
    startActivity(intent);
    finish();
  }
}
