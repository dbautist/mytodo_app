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
    // Default to today's date
    setDateText(TDUtil.getCurrentDateInMillis());

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
    }
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
    dateText.setText(TDUtil.convertMillisToDate(mItem.dateInMilliseconds));
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
    FragmentManager fm = getSupportFragmentManager();
    DateChooserDialogFragment editNameDialogFragment = new DateChooserDialogFragment();
    editNameDialogFragment.show(fm, "fragment_choose_date");

  }

  public void saveItem() {
    if (mItem == null)
      mItem = new TodoItem();

    mItem.title = titleText.getText().toString();
    mItem.dateInMilliseconds = TDUtil.convertDateToMillis(dateText.getText().toString());
    mItem.notes = notesText.getText().toString();
    mItem.priority = priorityText.getSelectedItem().toString();
    mItem.status = statusText.getSelectedItem().toString();

    boolean result = TodoManager.getInstance().addItem(mItem);
    if (result) {
      Intent intent = new Intent(this, TodoListActivity.class);
      startActivity(intent);
    } else {
      // This should be rare, but for good user experience, display an error message
      // TODO: Display error message
    }
  }

  public void cancelItem() {
    finish();
  }

  @Override
  public void onFinishDateChooserDialog(long dateInMillisecond) {
    setDateText(dateInMillisecond);
  }

  private void setDateText(long dateInMillisecond){
    dateText.setText(TDUtil.convertMillisToDate(dateInMillisecond));
  }
}
