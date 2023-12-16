package com.example.carduino.shared.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.shared.singletons.TripSingleton;

public class DialogUtilities {
    private static Boolean showingDialog = false;
    private static void showDialog(Activity activity, String message, String title, DialogInterface.OnClickListener yesListener, DialogInterface.OnClickListener noListener) {
        if(activity != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            // Set the message show for the Alert time
            builder.setMessage(message);

            // Set Alert Title
            builder.setTitle(title);

            // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
            builder.setCancelable(true);

            // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
            builder.setPositiveButton("Yes", yesListener);

            // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
            builder.setNegativeButton("No", noListener);

            activity.runOnUiThread(new Runnable() {
                public void run() {
                    // Create the Alert dialog
                    AlertDialog alertDialog = builder.create();
                    // Show the Alert Dialog box
                    alertDialog.show();

                    showingDialog = true;
                }
            });
        }
    }

    public static void showContinueTripSession(Activity activity) {
        if(!showingDialog) {
            showDialog(activity, "Would you like to continue last trip session", "Continue trip", (dialog, which) -> {
                try {
                    TripSingleton.getInstance().restoreTrip();
                    TripSingleton.getInstance().startTrip();
                    showingDialog = false;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }, (dialog, which) -> {
                dialog.cancel();
                TripSingleton.getInstance().startTrip();
                showingDialog = false;
            });
        }
    }

    public static Boolean isShowingDialog() {
        return showingDialog;
    }
}
