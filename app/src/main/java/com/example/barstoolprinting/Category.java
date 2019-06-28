package com.example.barstoolprinting;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.barstoolprinting.Utilities.CategoryAdapter;
import com.example.barstoolprinting.Utilities.Utility;

import java.util.ArrayList;
import java.util.List;

public class Category extends AppCompatActivity {
    private static long FINISH_TIMEOUT_IN_MILLIS = 30000;
    private static long ALERT_TIMEOUT_IN_MILLIS = 60000;
    private static Utility utility;
    private List<String> pathsToImages;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private AlertDialog alertDialog;
    protected BottomNavigationView navigation;

    Handler finishHandler = new Handler();
    Runnable finishRunnable = new Runnable() {

        @Override
        public void run() {
            alertDialog.dismiss();
            finish();
        }
    };

    Handler alertHandler = new Handler();
    Runnable alertRunnable = new Runnable() {

        @Override
        public void run() {
            AlertDialog.Builder dialog = new AlertDialog.Builder(Category.this);
            dialog.setIcon(R.drawable.stool_logo_orange);
            dialog.setTitle("Idle");
            dialog.setMessage("Do you need more time?");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finishHandler.removeCallbacks(finishRunnable);
                    alertHandler.removeCallbacks(alertRunnable);
                    alertHandler.postDelayed(alertRunnable, ALERT_TIMEOUT_IN_MILLIS);
                    dialog.cancel();
                }
            });
            alertDialog = dialog.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            finishHandler.postDelayed(finishRunnable, FINISH_TIMEOUT_IN_MILLIS);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);

        navigation = findViewById(R.id.navigation);

        String categoryPath = getResources().getString(R.string.products_folder) + "/" +
                getResources().getString(R.string.categories_folder) + "/" +
                getIntent().getStringExtra(getResources().getString(R.string.category_pref)) + "/" +
            getResources().getString(R.string.images_folder);
        utility = Utility.getInstance();
        pathsToImages = new ArrayList<>();
        List<String> files = utility.getURIOfAllFilesInInternalStorage(getApplicationContext(), categoryPath,
                getResources().getString(R.string.root_folder));
        for(String fileUri: files){
            pathsToImages.add(fileUri);
        }

        mAdapter = new CategoryAdapter(Category.this, pathsToImages);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                alertHandler.removeCallbacks(alertRunnable);
                alertHandler.postDelayed(alertRunnable, ALERT_TIMEOUT_IN_MILLIS);
                return false;
            }
        });

        alertHandler.postDelayed(alertRunnable, ALERT_TIMEOUT_IN_MILLIS);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        home();
                        return true;
                    case R.id.admin:
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Category.this);
                        alertDialog.setTitle("PASSWORD");
                        alertDialog.setMessage("Enter Password");

                        final EditText input = new EditText(Category.this);
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
                                        Intent switchActivity = new Intent(Category.this, Admin.class);
                                        startActivity(switchActivity);
                                    } else {
                                        Toast.makeText(Category.this,
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

    protected void home(){
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        finishHandler.removeCallbacks(finishRunnable);
        alertHandler.removeCallbacks(alertRunnable);
    }
}
