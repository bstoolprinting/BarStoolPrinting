package com.example.barstoolprinting;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;


public class AlexMain extends AppCompatActivity {
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private Set<String> joinList;
    private static NetworkStatus networkStatus;
    private Button products;
    private Button about;
    private Button join;
    private Button vendors;
    private Button admin;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alex_main);

        settings = getSharedPreferences(getResources().getString(R.string.app_name), 0);
        editor = settings.edit();

        //Retrieve the values
        if(settings.contains(getResources().getString(R.string.joinList))) {
            joinList = settings.getStringSet(getResources().getString(R.string.joinList), null);
        }
        else {
            joinList = new HashSet<String>();
        }

        networkStatus = NetworkStatus.getInstance();

        products = findViewById(R.id.products);
        products.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent switchActivity = new Intent(AlexMain.this, Products.class);
                startActivity(switchActivity);
            }
        });

        about = findViewById(R.id.about);
        about.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent switchActivity = new Intent(AlexMain.this, About.class);
                startActivity(switchActivity);
            }
        });

        join = findViewById(R.id.join);
        join.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(final android.view.View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AlexMain.this);
                alertDialog.setTitle("Email");
                alertDialog.setMessage("Please enter your email");

                final EditText input = new EditText(AlexMain.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input); // uncomment this line
                alertDialog.setIcon(R.drawable.stool_logo_orange);

                alertDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final String email = input.getText().toString();
                        if (email.length() > 0) {
                            networkStatus.isOnline(new NetworkStatus.NetworkCallback() {
                                @Override
                                public void handleConnected(final boolean _success) {
                                    // this needs to run on the ui thread because of ui components in it
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (_success) {
                                                String[] to = new String[]{getResources().getString(R.string.myEmail)};
                                                String subject = "Join";
                                                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                                                emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
                                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                                                emailIntent.putExtra(Intent.EXTRA_TEXT, email);
                                                emailIntent.setType("message/rfc822");

                                                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                                            } else {
                                                Toast.makeText(getApplicationContext(),
                                                        "No Internet!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                            joinList.add(email);
                            editor.putStringSet(getResources().getString(R.string.joinList), joinList);
                            editor.commit();
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

        vendors = findViewById(R.id.vendors);
        vendors.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent switchActivity = new Intent(AlexMain.this, Vendors.class);
                startActivity(switchActivity);
            }
        });

        admin = findViewById(R.id.admin);
        admin.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(final android.view.View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AlexMain.this);
                alertDialog.setTitle("PASSWORD");
                alertDialog.setMessage("Enter Password");

                final EditText input = new EditText(AlexMain.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input); // uncomment this line
                alertDialog.setIcon(R.drawable.stool_logo_orange);

                alertDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String password = input.getText().toString();
                        if (password.length() > 0) {
                            if (password.contentEquals(getResources().getString(R.string.password))) {
                                Intent switchActivity = new Intent(AlexMain.this, Admin.class);
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
