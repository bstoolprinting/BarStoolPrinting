package com.example.barstoolprinting.Deprecated;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.barstoolprinting.BaseActivity;
import com.example.barstoolprinting.R;

public class Old_Shows extends BaseActivity {
    private TextView text;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_picture);

        Initialize();

        image = findViewById(R.id.image);

        boolean imageFound = setImage(getResources().getString(R.string.shows_folder) + "/" +
                        getResources().getString(R.string.screen_folder),
                image);

        if(!imageFound){
            image.setBackgroundResource(R.drawable.tbd);
        }

        text = findViewById(R.id.text);
        text.setText(getResources().getString(R.string.title_shows));
    }

    protected void home(){
        finish();
    }
}
