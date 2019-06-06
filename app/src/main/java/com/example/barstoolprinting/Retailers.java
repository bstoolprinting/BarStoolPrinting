package com.example.barstoolprinting;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Retailers extends BaseActivity {
    private TextView text;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture);

        Initialize();

        image = findViewById(R.id.image);

        boolean imageFound = setImage(getResources().getString(R.string.retailers_folder),
                getResources().getString(R.string.retailers_screen),
                image);

        if(!imageFound){
            image.setBackgroundResource(R.drawable.tbd);
        }

        text = findViewById(R.id.text);
        text.setText(getResources().getString(R.string.title_retailers));
    }

    protected void home(){
        finish();
    }
}
