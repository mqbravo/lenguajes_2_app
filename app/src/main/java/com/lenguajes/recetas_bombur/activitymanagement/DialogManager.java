package com.lenguajes.recetas_bombur.activitymanagement;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.lenguajes.recetas_bombur.R;

public class DialogManager {


    public static AlertDialog createYesNoDialog(AppCompatActivity activity,
                                                DialogInterface.OnClickListener yesListener
                                                ,DialogInterface.OnClickListener noListener,
                                                String title, String message){

        AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity);
        builder.setMessage(message).setTitle(title).setCancelable(false)

                .setPositiveButton(R.string.yes, yesListener)
                .setNegativeButton(R.string.no, noListener);

        return builder.create();
    }

}
