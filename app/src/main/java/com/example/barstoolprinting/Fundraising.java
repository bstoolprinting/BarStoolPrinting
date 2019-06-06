package com.example.barstoolprinting;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Fundraising extends BaseActivity {
    private TextView text;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture);

        Initialize();

        image = findViewById(R.id.image);

        boolean imageFound = setImage(getResources().getString(R.string.fundraising_folder) + "/" +
                        getResources().getString(R.string.screen_folder),
                image);

        if(!imageFound){
            image.setBackgroundResource(R.drawable.tbd);
        }

        text = findViewById(R.id.text);
        text.setText(getResources().getString(R.string.title_fundraising));
    }

    protected void home(){
        finish();
    }
}
