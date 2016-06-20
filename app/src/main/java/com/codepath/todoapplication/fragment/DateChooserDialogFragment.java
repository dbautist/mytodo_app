package com.codepath.todoapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import com.codepath.todoapplication.R;
import com.codepath.todoapplication.utils.TDUtil;

/*
    Reference: http://guides.codepath.com/android/Using-DialogFragment
 */
public class DateChooserDialogFragment extends DialogFragment {
  private CalendarView mCalendarView;
  private Button mOkButton, mCancelButton;

  public interface DateChooserListener {
    void onFinishDateChooserDialog(long dateInMillisecond);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_calendar, container);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mCalendarView = (CalendarView) view.findViewById(R.id.calendarView);
    mCalendarView.setMinDate(TDUtil.getCurrentDateInMillis());
    mOkButton = (Button) view.findViewById(R.id.okButton);
    mOkButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        setDate();
      }
    });
    mCancelButton = (Button) view.findViewById(R.id.cancelButton);
    mCancelButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismiss();
      }
    });
  }

  private void setDate() {
    DateChooserListener listener = (DateChooserListener) getActivity();
    if (listener != null) {
      listener.onFinishDateChooserDialog(mCalendarView.getDate());
      dismiss();
    }
  }
}