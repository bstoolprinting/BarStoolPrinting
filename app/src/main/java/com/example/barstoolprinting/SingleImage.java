package com.example.barstoolprinting;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleImage extends BaseActivity {
    private TextView text;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_image);

        Initialize();

        image = findViewById(R.id.image);

        boolean imageFound = setImage(getIntent().getStringExtra(getResources().getString(R.string.screen_uri)),
                image);

        if(!imageFound){
            image.setBackgroundResource(R.drawable.tbd);
        }

        text = findViewById(R.id.text);
        text.setText(getIntent().getStringExtra(getResources().getString(R.string.text_message)));
    }

    protected void home(){
        finish();
    }
}
