package com.lenguajes.recetas_bombur.activitymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class IntentUtils {

    public static void openGallery(AppCompatActivity activity, int requestCode){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        activity.startActivityForResult(galleryIntent, requestCode);
    }
}
