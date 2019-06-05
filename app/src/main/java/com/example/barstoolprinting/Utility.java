package com.example.barstoolprinting;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

    public String saveFileToInternalStorage(Context base, String directoryName, String fileName, File file){
        ContextWrapper cw = new ContextWrapper(base);
        File directory = cw.getDir(directoryName, Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,fileName);

        FileOutputStream fos = null;
        try {
            InputStream in = new FileInputStream(file);
            fos = new FileOutputStream(mypath);
            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {
                fos.write(buf, 0, len);
            }

            in.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
    }

    public boolean isFileInInternalStorage(Context base, String directoryName, String fileName) {
        ContextWrapper cw = new ContextWrapper(base);
        File directory = cw.getDir(directoryName, Context.MODE_PRIVATE);

        if(directory.isDirectory()){
            for (File f : directory.listFiles()) {
                if (f.isFile() && f.getName().contentEquals(fileName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getURIOfFileInInternalStorage(Context base, String directoryName, String fileName) {
        String uri = "";
        ContextWrapper cw = new ContextWrapper(base);
        File directory = cw.getDir(directoryName, Context.MODE_PRIVATE);

        if(directory.isDirectory()){
            for (File f : directory.listFiles()) {
                if (f.isFile() && f.getName().contentEquals(fileName)) {
                    uri = f.getAbsolutePath();
                }
            }
        }
        return uri;
    }

    public String getURIOfDirectoryInInternalStorage(Context base, String directoryName) {
        String uri = "";
        ContextWrapper cw = new ContextWrapper(base);
        File directory = cw.getDir(directoryName, Context.MODE_PRIVATE);

        if(directory.isDirectory()){
            uri = directory.getAbsolutePath();
        }
        return uri;
    }

    public List<String> getURIOfAllFilesInInternalStorage(Context base, String directoryName) {
        List<String> list = new ArrayList<>();
        ContextWrapper cw = new ContextWrapper(base);
        File directory = cw.getDir(directoryName, Context.MODE_PRIVATE);

        if(directory.isDirectory()){
            for (File f : directory.listFiles()) {
                if (f.isFile()) {
                    list.add(f.getAbsolutePath());
                }
            }
        }
        return list;
    }
}
