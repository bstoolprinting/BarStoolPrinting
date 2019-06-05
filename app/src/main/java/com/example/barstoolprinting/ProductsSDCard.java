package com.example.barstoolprinting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ProductsSDCard extends AppCompatActivity {
    private static Utility utility;
    private ImageView banner;
    private ImageButton bottle;
    private ImageButton coasters;
    private ImageButton mugs;
    private ImageButton other;
    private ImageButton photo;
    private BottomNavigationView navigation;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_sdcard);

        utility = Utility.getInstance();

        banner = findViewById(R.id.banner);

        bottle = findViewById(R.id.bottle);
        bottle.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(ProductsSDCard.this, Category.class);
                intent.putExtra(getResources().getString(R.string.category_pref), getResources().getString(R.string.bottle_openers_folder));
                startActivity(intent);
            }
        });

        coasters = findViewById(R.id.coasters);
        coasters.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(ProductsSDCard.this, Category.class);
                intent.putExtra(getResources().getString(R.string.category_pref), getResources().getString(R.string.coasters_folder));
                startActivity(intent);
            }
        });

        mugs = findViewById(R.id.mugs);
        mugs.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(final android.view.View v) {
                Intent intent = new Intent(ProductsSDCard.this, Category.class);
                intent.putExtra(getResources().getString(R.string.category_pref), getResources().getString(R.string.mugs_folder));
                startActivity(intent);
            }
        });

        other = findViewById(R.id.other);
        other.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(ProductsSDCard.this, Category.class);
                intent.putExtra(getResources().getString(R.string.category_pref), getResources().getString(R.string.other_folder));
                startActivity(intent);
            }
        });

        photo = findViewById(R.id.photo);
        photo.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(ProductsSDCard.this, Category.class);
                intent.putExtra(getResources().getString(R.string.category_pref), getResources().getString(R.string.photo_slate_folder));
                startActivity(intent);
            }
        });

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        finish();
                        return true;
                    default: return true;
                }
            }
        });

        setImage(getResources().getString(R.string.banner_folder),
                getResources().getString(R.string.banner_screen),
                banner);
    }

    private void setImage(String folderName, String imageName, ImageView view) {
        String uri = utility.getURIOfFileInInternalStorage(getApplicationContext(),
                folderName,
                imageName);

        if(!uri.isEmpty()) {
            Picasso.with(getApplicationContext()).load("file://" + uri).into(view);
        }
    }
}
