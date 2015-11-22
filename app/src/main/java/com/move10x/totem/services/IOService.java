package com.move10x.totem.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Ravi on 10/11/2015.
 */
public class IOService {

    private String logTag = "ioService";

    public void writeImageToFile(String fileName, Bitmap image) {
        FileOutputStream out = null;
        try {
            File file = new File(android.os.Environment.getExternalStorageDirectory(), fileName);
            Log.d(logTag, "fileWritePath:" + file.getAbsolutePath());
            out = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 40, out); // bmp is your Bitmap instance
            out.flush();
        } catch (Exception e) {
            Log.e(logTag, e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeLargeImageToFile(String fileName, Bitmap image) {
        FileOutputStream out = null;
        try {
            File file = new File(android.os.Environment.getExternalStorageDirectory(), fileName);
            Log.d(logTag, "fileWritePath:" + file.getAbsolutePath());
            out = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
            out.flush();
        } catch (Exception e) {
            Log.e(logTag, e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean fileExists(String fileName){
        File file = new File(android.os.Environment.getExternalStorageDirectory(), fileName);
        return file.exists();
    }

    public  Bitmap readImageFromFile(String fileName) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        String filePath = android.os.Environment.getExternalStorageDirectory() + "/" + fileName;
        Log.d(logTag, "Reading file: " + filePath);
        return BitmapFactory.decodeFile(filePath, options);
    }

    public void removeFile(String fileName){
        File file = new File(android.os.Environment.getExternalStorageDirectory(), fileName);
        if(file.exists()){
            file.delete();
        }
    }

    public static Bitmap getResizedBitmap(Bitmap image) {
        int width = image.getWidth();
        int height = image.getHeight();
        float scaleWidth = ((float) 100) / width;
        float scaleHeight = ((float) 100) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(image, 0, 0, width, height,
                matrix, false);
        return resizedBitmap;
    }

}
