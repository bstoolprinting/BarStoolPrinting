package com.example.barstoolprinting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class About extends AppCompatActivity {
    private Button home;
    private ImageView about_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        about_text = findViewById(R.id.about_text);
        File storageDir = About.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if(storageDir == null) throw new AssertionError("Cannot read " + Environment.DIRECTORY_PICTURES);
        String path = storageDir.getAbsolutePath();
        String fileName = path + getResources().getString(R.string.about_file_name);
        File file = new File(fileName);

        if(file.exists()){
            about_text.setImageBitmap(BitmapFactory.decodeFile(fileName));
        }
        else {
            about_text.setBackgroundResource(R.drawable.about_text);
        }

        home = findViewById(R.id.home);
        home.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                finish();
            }
        });
    }
}
