package com.example.barstoolprinting.Deprecated;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.barstoolprinting.Home;
import com.example.barstoolprinting.R;
import com.example.barstoolprinting.Utilities.Utility;

import java.io.File;

public class Old_Loading extends AppCompatActivity {
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION = 1;
    private static Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_activity_loading);

        utility = Utility.getInstance();

        askForReadPermission();
    }

    private void askForReadPermission() {
        int readExternalStoragePermission = ContextCompat.checkSelfPermission(Old_Loading.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(readExternalStoragePermission!= PackageManager.PERMISSION_GRANTED)
        {
            // Request user to grant write external storage permission.
            ActivityCompat.requestPermissions(Old_Loading.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION);
        }
        else {
            save();
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
                    save();
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Old_Loading.this);
                    alertDialog.setTitle("Load SD card data");
                    alertDialog.setMessage("Are you sure you want to Bypass old_activity_loading data from sdcard?");
                    alertDialog.setIcon(R.drawable.stool_logo_orange);

                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent switchActivity = new Intent(Old_Loading.this, Home.class);
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

    private void save() {
        String rootFolderPath = Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.root_folder);
        File rootFolder = new File(rootFolderPath);
        if(rootFolder.isDirectory()) {
            File[] directories = rootFolder.listFiles();

            for (File directory : directories) {
                if(directory.isDirectory()) {
                    String directoryName = directory.getName();

                    if(directoryName.contentEquals(getResources().getString(R.string.products_folder))) {
                        File[] subDirectories = directory.listFiles();
                        for (File subDirectory : subDirectories) {
                            if (subDirectory.isDirectory()) {
                                String subDirectoryName = subDirectory.getName();
                                if (subDirectoryName.contentEquals(getResources().getString(R.string.button_folder))) {
                                    saveFirstFile(subDirectory,
                                            getResources().getString(R.string.products_folder),
                                            "products_button.png");
                                }
                            }
                        }
                    }
                    else if(directoryName.contentEquals(getResources().getString(R.string.about_folder))) {
                        savePair(directory, getResources().getString(R.string.about_folder),
                                "about_button.png",
                                "about_screen.png");
                    }
                    else if(directoryName.contentEquals(getResources().getString(R.string.join_folder))) {
                        File[] subDirectories = directory.listFiles();
                        for (File subDirectory : subDirectories) {
                            if (subDirectory.isDirectory()) {
                                String subDirectoryName = subDirectory.getName();
                                if (subDirectoryName.contentEquals(getResources().getString(R.string.button_folder))) {
                                    saveFirstFile(subDirectory,
                                            getResources().getString(R.string.join_folder),
                                            "join_button.png");
                                }
                            }
                        }
                    }
                    else if(directoryName.contentEquals(getResources().getString(R.string.banner_folder))) {
                        saveFirstFile(directory,
                                getResources().getString(R.string.banner_folder),
                                "banner_screen.png");
                    }
                    else if(directoryName.contentEquals(getResources().getString(R.string.fundraising_folder))) {
                        savePair(directory, getResources().getString(R.string.fundraising_folder),
                                "fundraising_button.png",
                                "fundraising_screen.png");
                    }
                    else if(directoryName.contentEquals(getResources().getString(R.string.retailers_folder))) {
                        savePair(directory, getResources().getString(R.string.retailers_folder),
                                "retailers_button.png",
                                "retailers_screen.png");
                    }
                    else if(directoryName.contentEquals(getResources().getString(R.string.shows_folder))) {
                        savePair(directory, getResources().getString(R.string.shows_folder),
                                "shows_button.png",
                                "shows_screen.png");
                    }
                    else if(directoryName.contentEquals(getResources().getString(R.string.categories_folder))) {
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

        Intent switchActivity = new Intent(Old_Loading.this, Home.class);
        startActivity(switchActivity);
    }

    private void savePair(File directory, String folderName, String buttonName, String screenName) {
        File[] subDirectories = directory.listFiles();
        for (File subDirectory : subDirectories) {
            if (subDirectory.isDirectory()) {
                String subDirectoryName = subDirectory.getName();
                if (subDirectoryName.contentEquals(getResources().getString(R.string.button_folder))) {
                    saveFirstFile(subDirectory,
                            folderName,
                            buttonName);
                } else if (subDirectoryName.contentEquals(getResources().getString(R.string.screen_folder))) {
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
                if(subDirectoryName.contentEquals(getResources().getString(R.string.bottle_openers_folder))) {
                    saveAllFiles(subDirectory, getResources().getString(R.string.bottle_openers_folder));
                }
                else if(subDirectoryName.contentEquals(getResources().getString(R.string.coasters_folder))) {
                    saveAllFiles(subDirectory, getResources().getString(R.string.coasters_folder));
                }
                else if(subDirectoryName.contentEquals(getResources().getString(R.string.mugs_folder))) {
                    saveAllFiles(subDirectory, getResources().getString(R.string.mugs_folder));
                }
                else if(subDirectoryName.contentEquals(getResources().getString(R.string.other_folder))) {
                    saveAllFiles(subDirectory, getResources().getString(R.string.other_folder));
                }
                else if(subDirectoryName.contentEquals(getResources().getString(R.string.photo_slate_folder))) {
                    saveAllFiles(subDirectory, getResources().getString(R.string.photo_slate_folder));
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
        String absPath = utility.saveFileToInternalStorage(getApplicationContext(),
                folderName,
                resourceName,
                file);
        System.out.println("Saved: " + absPath);
    }
}


