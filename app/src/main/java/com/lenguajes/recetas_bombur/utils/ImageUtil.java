package com.lenguajes.recetas_bombur.utils;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class ImageUtil {

    public static byte[] getBytesFromBitmap(Bitmap bitmap, Bitmap.CompressFormat compressFormat, int quality){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress( compressFormat, quality, baos);

        return baos.toByteArray();
    }

}
