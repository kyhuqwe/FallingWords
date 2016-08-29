package com.romansavin.fallingwords.view.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * @author Roman Savin
 * @since 29.08.2016.
 */
public class AlertDialogFragment extends DialogFragment {

  public static final String TAG = AlertDialogFragment.class.getSimpleName();

  private static final String MESSAGE_BUNDLE_KEY = "MESSAGE_BUNDLE_KEY";

  @Nullable private DialogInterface.OnClickListener clickListener;

  public static AlertDialogFragment newInstance(@NonNull final String message) {
    final AlertDialogFragment dialogFragment = new AlertDialogFragment();
    final Bundle arguments = new Bundle();
    arguments.putString(MESSAGE_BUNDLE_KEY, message);

    dialogFragment.setArguments(arguments);
    return dialogFragment;
  }

  @NonNull @Override public Dialog onCreateDialog(final Bundle savedInstanceState) {
    final AlertDialog.Builder builder= new AlertDialog.Builder(getContext())
        .setMessage(getArguments().getString(MESSAGE_BUNDLE_KEY));

    if (clickListener != null) {
      builder.setPositiveButton(android.R.string.ok, clickListener);
    }

    return builder.create();
  }

  public void setClickListener(@NonNull final DialogInterface.OnClickListener clickListener) {
    this.clickListener = clickListener;
  }
}
