package com.lenguajes.recetas_bombur;

import android.app.Application;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RecetasBomburApplication extends Application {
    public static String sessionToken="";
    public static String sessionUsername="";
    public static String url;
    @Override
    public void onCreate() {
        super.onCreate();
        url = getApplicationContext().getResources().getString(R.string.fengHerokuServer);
    }

    public static StorageReference getStorageReference(){
        return FirebaseStorage.getInstance().getReference();
    }

    public static String getSessionToken() {
        return sessionToken;
    }

    public static void setSessionToken(String sessionToken) {
        RecetasBomburApplication.sessionToken = sessionToken;
    }

    public static String getURL(){
        return url;
    }

    public static String getSessionUsername() {
        return sessionUsername;
    }

    public static void setSessionUsername(String sessionUsername) {
        RecetasBomburApplication.sessionUsername = sessionUsername;
    }
}
