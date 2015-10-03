package com.walmart.simpletodo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by tjing on 10/2/15.
 */
public class DeleteConfirmationDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                // Set Dialog Icon
                .setIcon(R.drawable.notification_template_icon_bg)
                        // Set Dialog Message
                .setMessage("Are you sure you want to delete this task?")

                        // Positive button
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity callingActivity = (MainActivity) getActivity();
                        callingActivity.OnDeleteTaskSumbit();
                    }
                })

                        // Negative Button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int which) {
                        DeleteConfirmationDialog.this.getDialog().cancel();
                    }
                }).create();
    }

}
