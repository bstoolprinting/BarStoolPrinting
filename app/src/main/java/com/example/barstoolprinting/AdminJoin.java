package com.example.barstoolprinting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Set;

public class AdminJoin extends AppCompatActivity {
    private Button send_list;
    private Button back;
    private static NetworkStatus networkStatus;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    Set<String> joinList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_join);

        settings = getSharedPreferences(getResources().getString(R.string.app_name), 0);
        editor = settings.edit();

        //Retrieve the values
        joinList = settings.getStringSet(getResources().getString(R.string.joinList), null);

        networkStatus = NetworkStatus.getInstance();

        send_list = findViewById(R.id.send_list);
        send_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!joinList.isEmpty()) {
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
                                        emailIntent.putExtra(Intent.EXTRA_TEXT, joinList.toString());
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

                }
                else {
                    Toast.makeText(getApplicationContext(),"Nothing to send!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        back = findViewById(R.id.back);
        back.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                finish();
            }
        });
    }
}
