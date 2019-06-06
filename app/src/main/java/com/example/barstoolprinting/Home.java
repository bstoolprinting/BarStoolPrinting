package com.example.barstoolprinting;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.barstoolprinting.Utilities.NetworkStatus;

import java.util.HashSet;
import java.util.Set;


public class Home extends BaseActivity {
    private Set<String> joinList;
    private ImageButton products;
    private ImageButton about;
    private ImageButton join;
    private ImageButton retailers;
    private ImageButton shows;
    private ImageButton fundraising;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Initialize();

        //Retrieve the values
        if(settings.contains(getResources().getString(R.string.joinList))) {
            joinList = settings.getStringSet(getResources().getString(R.string.joinList), null);
        }
        else {
            joinList = new HashSet<String>();
        }

        products = findViewById(R.id.products);
        products.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent switchActivity = new Intent(Home.this, ProductsSDCard.class);
                startActivity(switchActivity);
            }
        });

        about = findViewById(R.id.about);
        about.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent switchActivity = new Intent(Home.this, About.class);
                startActivity(switchActivity);
            }
        });

        join = findViewById(R.id.join);
        join.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(final android.view.View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle("Email");
                alertDialog.setMessage("Please enter your email");

                final EditText input = new EditText(Home.this);
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

        retailers = findViewById(R.id.retailers);
        retailers.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent switchActivity = new Intent(Home.this, Retailers.class);
                startActivity(switchActivity);
            }
        });

        shows = findViewById(R.id.shows);
        shows.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent switchActivity = new Intent(Home.this, Shows.class);
                startActivity(switchActivity);
            }
        });

        fundraising = findViewById(R.id.fundraising);
        fundraising.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent switchActivity = new Intent(Home.this, Fundraising.class);
                startActivity(switchActivity);
            }
        });

        setImages();
    }
    private void setImages(){
        setButtonImage(getResources().getString(R.string.products_folder),
                getResources().getString(R.string.products_button),
                products);

        setButtonImage(getResources().getString(R.string.about_folder),
                getResources().getString(R.string.about_button),
                about);

        setButtonImage(getResources().getString(R.string.join_folder),
                getResources().getString(R.string.join_button),
                join);

        setButtonImage(getResources().getString(R.string.retailers_folder),
                getResources().getString(R.string.retailers_button),
                retailers);

        setButtonImage(getResources().getString(R.string.shows_folder),
                getResources().getString(R.string.shows_button),
                shows);

        setButtonImage(getResources().getString(R.string.fundraising_folder),
                getResources().getString(R.string.fundraising_button),
                fundraising);
    }

    protected void home(){}
}
