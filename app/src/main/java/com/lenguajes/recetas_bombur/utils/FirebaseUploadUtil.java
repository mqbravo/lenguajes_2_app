package com.lenguajes.recetas_bombur.utils;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lenguajes.recetas_bombur.RecetasBomburApplication;

public class FirebaseUploadUtil {

    private static final StorageReference storageReference = RecetasBomburApplication.getStorageReference();

    public static UploadTask uploadToFirebase(String bucketName, String fileName, byte[] bytes){

        StorageReference newImageReference = storageReference.child(bucketName + "/" + fileName);

        return newImageReference.putBytes(bytes);
    }

}
