package com.example.barstoolprinting.Utilities;

import android.content.Context;
import android.content.ContextWrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class Utility {
    public static final int ONE_KB = 1024;

    private static Utility instance;

    public static synchronized Utility getInstance(){
        if(instance == null ) {
            instance = new Utility();
        }
        return instance;
    }

    private Utility(){
    }

    public void saveAllFilesToInternalStorage(Context base, File source) {
        ContextWrapper cw = new ContextWrapper(base);
        File destination = cw.getDir(source.getName(), Context.MODE_PRIVATE);
        copyDirectory(source, destination);
    }

    private void copyDirectory(File srcDir, File destDir) {
        List<String> exclusionList = null;
        try {
            if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath())) {
                final File[] srcFiles = srcDir.listFiles();
                if (srcFiles != null && srcFiles.length > 0) {
                    exclusionList = new ArrayList<>(srcFiles.length);
                    for (final File srcFile : srcFiles) {
                        final File copiedFile = new File(destDir, srcFile.getName());
                        exclusionList.add(copiedFile.getCanonicalPath());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            doCopyDirectory(srcDir, destDir, exclusionList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doCopyDirectory(final File srcDir, final File destDir, final List<String> exclusionList)
            throws IOException {
        // recurse
        final File[] srcFiles = srcDir.listFiles();
        if (srcFiles == null) {  // null if abstract pathname does not denote a directory, or if an I/O error occurs
            throw new IOException("Failed to list contents of " + srcDir);
        }
        if (destDir.exists()) {
            if (destDir.isDirectory() == false) {
                throw new IOException("Destination '" + destDir + "' exists but is not a directory");
            }
        } else {
            if (!destDir.mkdirs() && !destDir.isDirectory()) {
                throw new IOException("Destination '" + destDir + "' directory cannot be created");
            }
        }
        if (destDir.canWrite() == false) {
            throw new IOException("Destination '" + destDir + "' cannot be written to");
        }
        for (final File srcFile : srcFiles) {
            final File dstFile = new File(destDir, srcFile.getName());
            if (exclusionList == null || !exclusionList.contains(srcFile.getCanonicalPath())) {
                if (srcFile.isDirectory()) {
                    doCopyDirectory(srcFile, dstFile, exclusionList);
                } else {
                    doCopyFile(srcFile, dstFile);
                }
            }
        }
    }

    private void doCopyFile(final File srcFile, final File destFile)
            throws IOException {
        if (destFile.exists() && destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' exists but is a directory");
        }

        FileOutputStream fos = null;
        try {
            InputStream in = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            // Copy the bits from instream to outstream
            byte[] buf = new byte[ONE_KB];
            int len;

            while ((len = in.read(buf)) > 0) {
                fos.write(buf, 0, len);
            }

            in.close();
            fos.close();
            System.out.println("Saved: " + destFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            byte[] buf = new byte[ONE_KB];
            int len;

            while ((len = in.read(buf)) > 0) {
                fos.write(buf, 0, len);
            }

            in.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mypath.getAbsolutePath();
    }

    public String getURIOfFirstFileInInternalStorage(Context base, String directoryName) {
        String uri = "";
        ContextWrapper cw = new ContextWrapper(base);
        File directory = cw.getDir(directoryName, Context.MODE_PRIVATE);

        if(directory.isDirectory()){
            for (File f : directory.listFiles()) {
                if (f.isFile()) {
                    uri = f.getAbsolutePath();
                    break;
                }
            }
        }
        return uri;
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
