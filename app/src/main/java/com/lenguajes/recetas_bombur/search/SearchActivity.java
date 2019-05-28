package com.lenguajes.recetas_bombur.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lenguajes.recetas_bombur.R;
import com.lenguajes.recetas_bombur.activitymanagement.ToolbarManager;

public class SearchActivity extends AppCompatActivity {

    private RadioGroup mRadioGroup;
    private RadioButton mSelectedRadioBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ToolbarManager.setToolbar(this, "", true, R.id.toolbar_search);

        mRadioGroup = findViewById(R.id.search_radioGroup);
        mSelectedRadioBtn = findViewById(R.id.search_radioBtnName);
    }


    public void checkSelection(View view) {
        int checkID = mRadioGroup.getCheckedRadioButtonId();
        mSelectedRadioBtn = findViewById(checkID);
    }
}
