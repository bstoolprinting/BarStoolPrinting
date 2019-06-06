package com.example.barstoolprinting;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.barstoolprinting.Utilities.Utility;

import java.io.File;

public class Loading extends AppCompatActivity {
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION = 1;
    private static Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading2);

        utility = Utility.getInstance();

        askForReadPermission();
    }

    private void askForReadPermission() {
        int readExternalStoragePermission = ContextCompat.checkSelfPermission(Loading.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if(readExternalStoragePermission!= PackageManager.PERMISSION_GRANTED)
        {
            // Request user to grant write external storage permission.
            ActivityCompat.requestPermissions(Loading.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION);
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
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Loading.this);
                    alertDialog.setTitle("Are you sure?");
                    alertDialog.setMessage("You want to Bypass loading data from sdcard?");
                    alertDialog.setIcon(R.drawable.stool_logo_orange);

                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent switchActivity = new Intent(Loading.this, Home.class);
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
        if (rootFolder.isDirectory()) {
            utility.saveAllFilesToInternalStorage(getApplicationContext(), rootFolder);
            Intent switchActivity = new Intent(Loading.this, Home.class);
            startActivity(switchActivity);
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Loading.this);
            alertDialog.setTitle(rootFolderPath + " not found!");
            alertDialog.setMessage("Old_Loading with internal files");
            alertDialog.setIcon(R.drawable.stool_logo_orange);

            alertDialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    Intent switchActivity = new Intent(Loading.this, Home.class);
                    startActivity(switchActivity);
                }
            });

            alertDialog.show();
        }
    }
}
