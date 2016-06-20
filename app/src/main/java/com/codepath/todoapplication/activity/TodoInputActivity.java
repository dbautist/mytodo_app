package com.codepath.todoapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.codepath.todoapplication.R;
import com.codepath.todoapplication.fragment.AlertDialogFragment;
import com.codepath.todoapplication.fragment.DateChooserDialogFragment;
import com.codepath.todoapplication.model.TodoItem;
import com.codepath.todoapplication.model.TodoManager;
import com.codepath.todoapplication.utils.TDUtil;

public class TodoInputActivity extends AppCompatActivity implements DateChooserDialogFragment.DateChooserListener {
  private EditText titleText, notesText;
  private Spinner priorityText, statusText;
  private TextView dateText;
  private ArrayAdapter<CharSequence> mStatusAdapter, mPriorityAdapter;
  private TodoItem mItem;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_input);

    titleText = (EditText) findViewById(R.id.titleText);
    dateText = (TextView) findViewById(R.id.dateText);
    notesText = (EditText) findViewById(R.id.notesText);
    priorityText = (Spinner) findViewById(R.id.priorityText);
    mPriorityAdapter = ArrayAdapter.createFromResource(this, R.array.priority_array, android.R.layout.simple_spinner_item);
    mPriorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    priorityText.setAdapter(mPriorityAdapter);

    statusText = (Spinner) findViewById(R.id.statusText);
    mStatusAdapter = ArrayAdapter.createFromResource(this, R.array.status_array, android.R.layout.simple_spinner_item);
    mStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    statusText.setAdapter(mStatusAdapter);

    mItem = TodoManager.getInstance().getSelectedItem();
    if (mItem != null) {
      populateItem();
    } else {
      mItem = new TodoItem();
    }

    // init date text
    setDateText(TDUtil.getCurrentDateInMillis());
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.activity_input_actions, menu);

    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_save:
        saveItem();
        return true;
      case R.id.action_cancel:
        cancelItem();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void populateItem() {
    titleText.setText(mItem.title);
    dateText.setText(TDUtil.convertMillisToDateString(mItem.dateInMilliseconds));
    notesText.setText(mItem.notes);

    if (mItem.priority != null) {
      int priorityPos = mPriorityAdapter.getPosition(mItem.priority);
      priorityText.setSelection(priorityPos);
    }

    if (mItem.status != null) {
      int statusPos = mStatusAdapter.getPosition(mItem.status);
      statusText.setSelection(statusPos);
    }
  }

  public void onSetDate(View view) {
    DateChooserDialogFragment dialogFragment = DateChooserDialogFragment.newInstance(mItem.dateInMilliseconds);
    FragmentManager fm = getSupportFragmentManager();
    dialogFragment.show(fm, "calendar_dialog_fragment");

  }

  public void saveItem() {
    mItem.title = titleText.getText().toString();
    if (mItem.title == null || mItem.title.isEmpty()) {
      AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance("Please fill out the To-Do item.");
      FragmentManager fm = getSupportFragmentManager();
      alertDialogFragment.show(fm, "alert_dialog_fragment");
      return;
    }

    mItem.dateInMilliseconds = TDUtil.convertDateStringToMillis(dateText.getText().toString());
    mItem.notes = notesText.getText().toString();
    mItem.priority = priorityText.getSelectedItem().toString();
    mItem.status = statusText.getSelectedItem().toString();

    TodoManager.getInstance().updateItem(mItem);

    Intent intent = new Intent(this, TodoListActivity.class);
    startActivity(intent);
  }

  public void cancelItem() {
    finish();
  }

  @Override
  public void onFinishDateChooserDialog(long dateInMillisecond) {
    setDateText(dateInMillisecond);
  }

  private void setDateText(long dateInMillisecond) {
    mItem.dateInMilliseconds = dateInMillisecond;
    dateText.setText(TDUtil.convertMillisToDateString(dateInMillisecond));
  }
}
