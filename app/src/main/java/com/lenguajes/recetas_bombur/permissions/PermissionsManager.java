package com.lenguajes.recetas_bombur.permissions;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


/**
 * Created by Mauricio on 15/4/2018.
 */

public class PermissionsManager{
    private final static int STORAGE_EXTERNAL_PERMISSION = 1;
    private AppCompatActivity activity;

    public PermissionsManager(AppCompatActivity activity) {
        this.activity= activity;
    }

    /**
     * Returns true if we already have the needed permissions, else, returns the result on the
     * request to the user
     */
    public boolean checkAndRequestStoragePermissions(){
        //Already granted
        if(isGranted(Manifest.permission.READ_EXTERNAL_STORAGE))
            return true;

        //Ask the user
        else
            return requestStoragePermissions();
    }

    /**
     * Requests the permission
     * @return
     */
    private boolean requestStoragePermissions(){
        //If the user requires to see why we need this permission
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE)){

            new AlertDialog.Builder(activity)
                    //TODO change this from the string values
                    //Title to the explanation
                    .setTitle("Permission needed")

                    //Explanation
                    .setMessage("This permission is needed in order to work with your photos")

                    //If he says "ok", show the permissions dialog
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    STORAGE_EXTERNAL_PERMISSION);
                        }
                    })

                    //If he says "stfu", dismiss the dialog and false the permissions
                    .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss())

                    .create().show(); //Remember to show the dialog
        }

        //If there is no need to explain why we need the permission, just request it
        else
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    STORAGE_EXTERNAL_PERMISSION);


        //Returns whether the permissions were granted
        return isGranted(Manifest.permission.READ_EXTERNAL_STORAGE);
    }


    private boolean isGranted(String permissionID){
        return ContextCompat.checkSelfPermission(activity, permissionID)
                == PackageManager.PERMISSION_GRANTED;
    }

}
