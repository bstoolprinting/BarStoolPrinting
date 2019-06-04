package com.example.barstoolprinting;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Utility {

    private static Utility instance;

    public static synchronized Utility getInstance(){
        if(instance == null ) {
            instance = new Utility();
        }
        return instance;
    }

    private Utility(){
    }

    public String saveToInternalStorage(Context base, String directoryName, String fileName, Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(base);
        File directory = cw.getDir(directoryName, Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
}
