package com.example.barstoolprinting;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.barstoolprinting.Utilities.NetworkStatus;
import com.example.barstoolprinting.Utilities.Utility;
import com.squareup.picasso.Picasso;

abstract class BaseActivity extends AppCompatActivity {
    protected SharedPreferences settings;
    protected SharedPreferences.Editor editor;
    protected static NetworkStatus networkStatus;
    protected static Utility utility;
    protected ImageView banner;
    protected BottomNavigationView navigation;

    protected void Initialize() {
        settings = getSharedPreferences(getResources().getString(R.string.app_name), 0);
        editor = settings.edit();
        networkStatus = NetworkStatus.getInstance();
        utility = Utility.getInstance();
        banner = findViewById(R.id.banner);
        navigation = findViewById(R.id.navigation);

        setImage(getResources().getString(R.string.banner_folder),
                getResources().getString(R.string.banner_screen),
                banner);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        home();
                        return true;
                    case R.id.admin:
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(BaseActivity.this);
                        alertDialog.setTitle("PASSWORD");
                        alertDialog.setMessage("Enter Password");

                        final EditText input = new EditText(BaseActivity.this);
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
                                        Intent switchActivity = new Intent(BaseActivity.this, Admin.class);
                                        startActivity(switchActivity);
                                    } else {
                                        Toast.makeText(BaseActivity.this,
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
                        return true;
                    default: return true;
                }
            }
        });
    }

    protected abstract void home();

    protected boolean setImage(String folderName, String imageName, ImageView view) {
        String uri = utility.getURIOfFileInInternalStorage(BaseActivity.this,
                folderName,
                imageName);

        if(!uri.isEmpty()) {
            Picasso.with(BaseActivity.this).load("file://" + uri).into(view);
            return true;
        }
        return false;
    }

    protected boolean setButtonImage(String folderName, String imageName, ImageView view) {
        String uri = utility.getURIOfFileInInternalStorage(BaseActivity.this,
                folderName,
                imageName);

        if(!uri.isEmpty()) {
            Picasso.with(BaseActivity.this).load("file://" + uri).fit().into(view);
            return true;
        }
        return false;
    }
}
