package com.example.barstoolprinting.Deprecated;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.barstoolprinting.Admin;
import com.example.barstoolprinting.R;


public class Directory extends AppCompatActivity {
    private Button etsy;
    private Button products;
    private Button join;
    private Button contact;
    private Button admin;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory);

        etsy = findViewById(R.id.etsy);
        etsy.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Uri uri = Uri.parse(getResources().getString(R.string.url));
                Intent openEtsy = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(openEtsy);
                Toast.makeText(Directory.this, "Opening...", Toast.LENGTH_LONG).show();
            }
        });

        products = findViewById(R.id.products);
        products.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent switchActivity = new Intent(Directory.this, Products.class);
                startActivity(switchActivity);
            }
        });

        join = findViewById(R.id.join);
        join.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(final android.view.View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Directory.this);
                alertDialog.setTitle("Email");
                alertDialog.setMessage("Please enter your email");

                final EditText input = new EditText(Directory.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input); // uncomment this line
                alertDialog.setIcon(R.drawable.stool_logo);

                alertDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String email = input.getText().toString();
                        if (email.length() > 0) {
                            String[] to = new String[]{getResources().getString(R.string.myEmail)};
                            String subject = "Join";
                            Intent emailIntent = new Intent(Intent.ACTION_SEND);
                            emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                            emailIntent.putExtra(Intent.EXTRA_TEXT, email);
                            emailIntent.setType("message/rfc822");

                            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                        }
                    }
                });

                alertDialog.setNegativeButton("Cancel",  new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.show();
            }
        });

        admin = findViewById(R.id.admin);
        admin.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(final android.view.View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Directory.this);
                alertDialog.setTitle("PASSWORD");
                alertDialog.setMessage("Enter Password");

                final EditText input = new EditText(Directory.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input); // uncomment this line
                alertDialog.setIcon(R.drawable.stool_logo);

                alertDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String password = input.getText().toString();
                        if (password.length() > 0) {
                            if (password.contentEquals(getResources().getString(R.string.password))) {
                                Intent switchActivity = new Intent(Directory.this, Admin.class);
                                startActivity(switchActivity);
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Wrong Password!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                alertDialog.setNegativeButton("Cancel",  new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.show();
            }
        });
    }
}
