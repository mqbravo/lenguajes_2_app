package com.lenguajes.recetas_bombur;

import android.app.Application;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RecetasBomburApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static StorageReference getStorageReference(){
        return FirebaseStorage.getInstance().getReference();
    }
}
