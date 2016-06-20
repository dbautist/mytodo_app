package com.codepath.todoapplication.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class AlertDialogFragment extends DialogFragment {
  public interface AlertDialogListener {
    void onPositiveButtonSelected();
  }

  public static AlertDialogFragment newInstance(String message) {
    AlertDialogFragment fragment = new AlertDialogFragment();
    Bundle args = new Bundle();
    args.putString("Message", message);
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    String message = getArguments().getString("Message");

    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
    alertDialogBuilder.setTitle("");
    alertDialogBuilder.setMessage(message);
    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        onPositiveButtonClickEvent();
        dialog.dismiss();
      }
    });
    alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
      }
    });

    return alertDialogBuilder.create();
  }

  private void onPositiveButtonClickEvent() {
    if (getActivity() instanceof AlertDialogListener) {
      AlertDialogListener listener = (AlertDialogListener) getActivity();
      if (listener != null) {
        listener.onPositiveButtonSelected();
      }
    }
  }
}
