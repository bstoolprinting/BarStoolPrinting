package com.example.barstoolprinting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;


public class Admin extends AppCompatActivity {
    private Button admin_products;
    private Button admin_about;
    private Button admin_join;
    private Button admin_vendors;
    private Button home;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        admin_about = findViewById(R.id.admin_about);
        admin_about.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent switchActivity = new Intent(Admin.this, AdminAbout.class);
                startActivity(switchActivity);
            }
        });

        admin_join = findViewById(R.id.admin_join);
        admin_join.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent switchActivity = new Intent(Admin.this, AdminJoin.class);
                startActivity(switchActivity);
            }
        });

        admin_vendors = findViewById(R.id.admin_vendors);
        admin_vendors.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent switchActivity = new Intent(Admin.this, AdminRetailers.class);
                startActivity(switchActivity);
            }
        });

        home = findViewById(R.id.home);
        home.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                finish();
            }
        });
    }
}
