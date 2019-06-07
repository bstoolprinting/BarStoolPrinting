package com.example.barstoolprinting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class Products extends BaseActivity {
    private ImageButton bottle;
    private ImageButton coasters;
    private ImageButton mugs;
    private ImageButton other;
    private ImageButton photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Initialize();

        bottle = findViewById(R.id.bottle);
        bottle.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(Products.this, Category.class);
                intent.putExtra(getResources().getString(R.string.category_pref), getResources().getString(R.string.bottle_openers_folder));
                startActivity(intent);
            }
        });

        coasters = findViewById(R.id.coasters);
        coasters.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(Products.this, Category.class);
                intent.putExtra(getResources().getString(R.string.category_pref), getResources().getString(R.string.coasters_folder));
                startActivity(intent);
            }
        });

        mugs = findViewById(R.id.mugs);
        mugs.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(final android.view.View v) {
                Intent intent = new Intent(Products.this, Category.class);
                intent.putExtra(getResources().getString(R.string.category_pref), getResources().getString(R.string.mugs_folder));
                startActivity(intent);
            }
        });

        other = findViewById(R.id.other);
        other.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(Products.this, Category.class);
                intent.putExtra(getResources().getString(R.string.category_pref), getResources().getString(R.string.other_folder));
                startActivity(intent);
            }
        });

        photo = findViewById(R.id.photo);
        photo.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(Products.this, Category.class);
                intent.putExtra(getResources().getString(R.string.category_pref), getResources().getString(R.string.photo_slate_folder));
                startActivity(intent);
            }
        });
    }

    protected void home(){
        finish();
    }
}
