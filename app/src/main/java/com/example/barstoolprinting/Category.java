package com.example.barstoolprinting;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

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
            dialog.setMessage("Still there?");
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
    }

    @Override
    public void onPause() {
        super.onPause();
        finishHandler.removeCallbacks(finishRunnable);
        alertHandler.removeCallbacks(alertRunnable);
    }
}
