package com.lenguajes.recetas_bombur.activitymanagement;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ToolbarManager {

    /**
     * Sets the toolbar on an activity and gives it support for previous API versions
     * @param activity The activity to place the toolbar in
     * @param title The text to be displayed
     * @param hasUpButton Whether or not has up button
     * @param toolbarIDRes The toolbar to be inserted
     */
    public static void setToolbar(AppCompatActivity activity, String title, boolean hasUpButton,
                                                int toolbarIDRes){

        Toolbar toolbar = activity.findViewById(toolbarIDRes);

        //Get support for previous versions
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(title);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(hasUpButton);
    }

    /**
     * Sets the toolbar on a fragment and gives it support for previous API versions
     * @param fragment The fragment to place the toolbar in
     * @param title The text to be displayed
     * @param hasUpButton Whether or not has up button
     * @param view The inflated layout to
     * @param toolbarIDRes The toolbar to be inserted
     */
    public static void setToolbar(Fragment fragment, String title, boolean hasUpButton,
                                                View view, int toolbarIDRes){

        Toolbar toolbar = view.findViewById(toolbarIDRes);
        AppCompatActivity activity = ((AppCompatActivity)fragment.getActivity());

        //Get support for previous versions
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(title);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(hasUpButton);
    }
}
