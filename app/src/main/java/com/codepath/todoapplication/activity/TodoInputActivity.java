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
  private EditText mTitleText, mNotesText;
  private Spinner mPriorityText, mStatusText;
  private TextView mDateText;
  private ArrayAdapter<CharSequence> mStatusAdapter, mPriorityAdapter;
  private TodoItem mItem;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_input);
    initView();

    mItem = TodoManager.getInstance().getSelectedItem();
    if (mItem != null) {
      populateItem();
    } else {
      mItem = new TodoItem();
      setDateText(TDUtil.getCurrentDateInMillis());
    }
  }

  private void initView() {
    mTitleText = (EditText) findViewById(R.id.titleText);
    mDateText = (TextView) findViewById(R.id.dateText);
    mNotesText = (EditText) findViewById(R.id.notesText);
    mPriorityText = (Spinner) findViewById(R.id.priorityText);
    mPriorityAdapter = ArrayAdapter.createFromResource(this, R.array.priority_array, android.R.layout.simple_spinner_item);
    mPriorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    mPriorityText.setAdapter(mPriorityAdapter);

    mStatusText = (Spinner) findViewById(R.id.statusText);
    mStatusAdapter = ArrayAdapter.createFromResource(this, R.array.status_array, android.R.layout.simple_spinner_item);
    mStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    mStatusText.setAdapter(mStatusAdapter);
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
    mTitleText.setText(mItem.title);
    // set cursor to the end of the text
    mTitleText.setSelection(mTitleText.getText().length());
    mDateText.setText(TDUtil.convertMillisToDateString(mItem.dateInMilliseconds));
    mNotesText.setText(mItem.notes);

    if (mItem.priority != null) {
      int priorityPos = mPriorityAdapter.getPosition(mItem.priority);
      mPriorityText.setSelection(priorityPos);
    }

    if (mItem.status != null) {
      int statusPos = mStatusAdapter.getPosition(mItem.status);
      mStatusText.setSelection(statusPos);
    }
  }

  public void onSetDate(View view) {
    DateChooserDialogFragment dialogFragment = DateChooserDialogFragment.newInstance(mItem.dateInMilliseconds);
    FragmentManager fm = getSupportFragmentManager();
    dialogFragment.show(fm, "calendar_dialog_fragment");

  }

  public void saveItem() {
    mItem.title = mTitleText.getText().toString();
    if (mItem.title == null || mItem.title.isEmpty()) {
      AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(getString(R.string.save_prompt));
      FragmentManager fm = getSupportFragmentManager();
      alertDialogFragment.show(fm, "alert_dialog_fragment");
      return;
    }

    mItem.dateInMilliseconds = TDUtil.convertDateStringToMillis(mDateText.getText().toString());
    mItem.notes = mNotesText.getText().toString();
    mItem.priority = mPriorityText.getSelectedItem().toString();
    mItem.status = mStatusText.getSelectedItem().toString();

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
    mDateText.setText(TDUtil.convertMillisToDateString(dateInMillisecond));
  }
}
