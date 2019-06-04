package com.example.barstoolprinting;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SplashScreen extends AppCompatActivity {
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION = 1;
    private static Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        utility = Utility.getInstance();

        askForReadPermission();
    }

    private void askForReadPermission() {
        int readExternalStoragePermission = ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(readExternalStoragePermission!= PackageManager.PERMISSION_GRANTED)
        {
            // Request user to grant write external storage permission.
            ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION);
        }
        else {
            Intent switchActivity = new Intent(SplashScreen.this, AlexMain.class);
            startActivity(switchActivity);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    String rootFolderPath = Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.root_folder);
                    File rootFolder = new File(rootFolderPath);
                    if(rootFolder.isDirectory()) {
                        File[] directories = rootFolder.listFiles();

                        for (File directory : directories) {
                            if(directory.isDirectory()) {
                                String directoryName = directory.getName();

                                if(directoryName.contains(getResources().getString(R.string.about_folder))) {
                                    savePair(directory, getResources().getString(R.string.about_folder),
                                            getResources().getString(R.string.about_button),
                                            getResources().getString(R.string.about_screen));
                                }
                                else if(directoryName.contains(getResources().getString(R.string.banner_folder))) {
                                    saveFirstFile(directory,
                                            getResources().getString(R.string.banner_folder),
                                            getResources().getString(R.string.banner_screen));
                                }
                                else if(directoryName.contains(getResources().getString(R.string.fundraising_folder))) {
                                    savePair(directory, getResources().getString(R.string.fundraising_folder),
                                            getResources().getString(R.string.fundraising_button),
                                            getResources().getString(R.string.fundraising_screen));
                                }
                                else if(directoryName.contains(getResources().getString(R.string.retailers_folder))) {
                                    savePair(directory, getResources().getString(R.string.retailers_folder),
                                            getResources().getString(R.string.retailers_button),
                                            getResources().getString(R.string.retailers_screen));
                                }
                                else if(directoryName.contains(getResources().getString(R.string.shows_folder))) {
                                    savePair(directory, getResources().getString(R.string.shows_folder),
                                            getResources().getString(R.string.shows_button),
                                            getResources().getString(R.string.shows_screen));
                                }
                                else if(directoryName.contains(getResources().getString(R.string.categories_folder))) {
                                    saveCategories(directory, getResources().getString(R.string.categories_folder));
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),
                                            directoryName + " not parsed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),
                                rootFolderPath + " not found!", Toast.LENGTH_SHORT).show();
                    }

                    Intent switchActivity = new Intent(SplashScreen.this, AlexMain.class);
                    startActivity(switchActivity);
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashScreen.this);
                    alertDialog.setTitle("Load SD card data");
                    alertDialog.setMessage("Are you sure you want to Bypass loading data from sdcard?");
                    alertDialog.setIcon(R.drawable.stool_logo_orange);

                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent switchActivity = new Intent(SplashScreen.this, AlexMain.class);
                            startActivity(switchActivity);
                        }
                    });

                    alertDialog.setNegativeButton("No",  new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            askForReadPermission();
                        }
                    });

                    alertDialog.show();
                }
                return;
            }

        }
    }

    private void savePair(File directory, String folderName, String buttonName, String screenName) {
        File[] subDirectories = directory.listFiles();
        for (File subDirectory : subDirectories) {
            if (subDirectory.isDirectory()) {
                String subDirectoryName = subDirectory.getName();
                if (subDirectoryName.contains(getResources().getString(R.string.button_folder))) {
                    saveFirstFile(subDirectory,
                            folderName,
                            buttonName);
                } else if (subDirectoryName.contains(getResources().getString(R.string.screen_folder))) {
                    saveFirstFile(subDirectory,
                            folderName,
                            screenName);
                }
            }
        }
    }

    private void saveCategories(File directory, String folderName) {
        File[] subDirectories = directory.listFiles();
        for (File subDirectory : subDirectories) {
            if (subDirectory.isDirectory()) {
                String subDirectoryName = subDirectory.getName();
                if(subDirectoryName.contains(getResources().getString(R.string.bottle_opener_folder))) {
                    String subFolderName = folderName + "/" + getResources().getString(R.string.bottle_opener_folder);
                    saveAllFiles(subDirectory, subFolderName);
                }
                else if(subDirectoryName.contains(getResources().getString(R.string.coaster_folder))) {
                    String subFolderName = folderName + "/" + getResources().getString(R.string.coaster_folder);
                    saveAllFiles(subDirectory, subFolderName);
                }
                else if(subDirectoryName.contains(getResources().getString(R.string.mugs_folder))) {
                    String subFolderName = folderName + "/" + getResources().getString(R.string.mugs_folder);
                    saveAllFiles(subDirectory, subFolderName);
                }
                else if(subDirectoryName.contains(getResources().getString(R.string.other_folder))) {
                    String subFolderName = folderName + "/" + getResources().getString(R.string.other_folder);
                    saveAllFiles(subDirectory, subFolderName);
                }
                else if(subDirectoryName.contains(getResources().getString(R.string.photo_slate_folder))) {
                    String subFolderName = folderName + "/" + getResources().getString(R.string.photo_slate_folder);
                    saveAllFiles(subDirectory, subFolderName);
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            subDirectoryName + " not parsed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void saveFirstFile(File directory, String folderName, String resourceName) {
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                saveFile(file, folderName,resourceName );
                break;
            }
        }
    }

    private void saveAllFiles(File directory, String folderName) {
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                saveFile(file, folderName, file.getName() );
            }
        }
    }

    private void saveFile(File file, String folderName, String resourceName) {
        try {
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(file));
            String absPath = utility.saveToInternalStorage(getApplicationContext(),
                    folderName,
                    resourceName,
                    b);
            System.out.println("Saved: " + absPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}


