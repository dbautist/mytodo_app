package com.codepath.todoapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.codepath.todoapplication.R;
import com.codepath.todoapplication.fragment.AlertDialogFragment;
import com.codepath.todoapplication.model.TodoItem;
import com.codepath.todoapplication.model.TodoManager;
import com.codepath.todoapplication.utils.TDUtil;

public class TodoDetailsActivity extends AppCompatActivity implements AlertDialogFragment.AlertDialogListener {
  private static final String TAG = "DETAILS";
  private TodoItem mItem;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_details);

    mItem = TodoManager.getInstance().getSelectedItem();
    if (mItem != null) {
      populateDetails(mItem);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.activity_details_actions, menu);

    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_edit:
        editItem();
        return true;
      case R.id.action_delete:
        deleteItem();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void populateDetails(TodoItem item) {
    ((TextView) findViewById(R.id.titleText)).setText(item.title);
    ((TextView) findViewById(R.id.dateText)).setText(TDUtil.convertMillisToDateString(item.dateInMilliseconds));
    ((TextView) findViewById(R.id.notesText)).setText(item.notes);
    ((TextView) findViewById(R.id.priorityText)).setText(item.priority);
    ((TextView) findViewById(R.id.statusText)).setText(item.status);
  }

  public void editItem() {
    Intent intent = new Intent(this, TodoInputActivity.class);
    startActivity(intent);
  }

  public void deleteItem() {

    AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance("Are you sure you want to delete this item?");
    FragmentManager fm = getSupportFragmentManager();
    alertDialogFragment.show(fm, "alert_dialog_fragment");
  }

  @Override
  public void onPositiveButtonSelected() {
    TodoManager.getInstance().removeItem(mItem);
    TodoDetailsActivity.this.finish();
  }
}
