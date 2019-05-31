package com.example.barstoolprinting;

import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class Vendors extends AppCompatActivity {
    private Button home;
    private ImageView vendors_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendors);

        vendors_text = findViewById(R.id.vendors_text);
        File storageDir = Vendors.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if(storageDir == null) throw new AssertionError("Cannot read " + Environment.DIRECTORY_PICTURES);
        String path = storageDir.getAbsolutePath();
        String fileName = path + getResources().getString(R.string.vendors_file_name);
        File file = new File(fileName);

        if(file.exists()){
            vendors_text.setImageBitmap(BitmapFactory.decodeFile(fileName));
        }
        else {
            vendors_text.setBackgroundResource(R.drawable.tbd);
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
