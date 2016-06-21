package com.codepath.todoapplication.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.codepath.todoapplication.utils.TDUtil;

import java.util.Calendar;

/*
    Reference: http://guides.codepath.com/android/Using-DialogFragment
 */
public class DateChooserDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

  public interface DateChooserListener {
    void onFinishDateChooserDialog(long dateInMillisecond);
  }

  public static DateChooserDialogFragment newInstance(long dateInMilliseconds) {
    DateChooserDialogFragment frag = new DateChooserDialogFragment();
    Bundle args = new Bundle();
    args.putLong("DateInMilliseconds", dateInMilliseconds);
    frag.setArguments(args);
    return frag;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the current date as the default date in the picker
    final Calendar c = Calendar.getInstance();
    if (getArguments() != null) {
      long dateInMilliseconds = getArguments().getLong("DateInMilliseconds");
      if (dateInMilliseconds != 0L) {
        // Set to params
        c.setTimeInMillis(dateInMilliseconds);
      }
    }

    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
    // Subtract a second to avoid IllegalArgumentException
    datePickerDialog.getDatePicker().setMinDate(TDUtil.getCurrentDateInMillis() - 1000);
    return datePickerDialog;
  }

  public void onDateSet(DatePicker view, int year, int month, int day) {
    Calendar c = Calendar.getInstance();
    c.set(year, month, day);

    DateChooserListener listener = (DateChooserListener) getActivity();
    if (listener != null) {
      listener.onFinishDateChooserDialog(c.getTimeInMillis());
    }
  }
}
