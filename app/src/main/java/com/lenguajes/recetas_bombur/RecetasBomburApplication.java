package com.lenguajes.recetas_bombur;

import android.app.Application;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RecetasBomburApplication extends Application {
    public static String currentToken="";
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static StorageReference getStorageReference(){
        return FirebaseStorage.getInstance().getReference();
    }

    public static void setCurrentToken(String newToken){
        currentToken = newToken;
    }

    public static String getCurrentToken(){
        return currentToken;
    }
}
