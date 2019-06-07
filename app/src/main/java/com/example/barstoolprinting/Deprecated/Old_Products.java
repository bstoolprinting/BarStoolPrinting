package com.example.barstoolprinting.Deprecated;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.barstoolprinting.Utilities.NetworkStatus;
import com.example.barstoolprinting.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Old_Products extends AppCompatActivity implements Old_EditImageAdapter.OnItemClickListener {
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private Set<String> productDatas;
    private static NetworkStatus networkStatus;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private ProgressBar mProgressCircle;

    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<Old_Upload> mOld_Uploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_activity_products);

        settings = getSharedPreferences(getResources().getString(R.string.app_name), 0);
        editor = settings.edit();

        //Retrieve the values
        productDatas = settings.getStringSet(getResources().getString(R.string.products_data), null);

        networkStatus = NetworkStatus.getInstance();

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle);

        mOld_Uploads = new ArrayList<>();

        if(getIntent().getBooleanExtra("edit", false)) {
            Old_EditImageAdapter adapter = new Old_EditImageAdapter(Old_Products.this, mOld_Uploads);
            adapter.setOnItemClickListener(Old_Products.this);
            mAdapter = adapter;
        }
        else {
            mAdapter = new Old_ImageAdapter(Old_Products.this, mOld_Uploads);
        }

        mRecyclerView.setAdapter(mAdapter);

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        networkStatus.isOnline(new NetworkStatus.NetworkCallback() {
            @Override
            public void handleConnected(final boolean _success) {
                // this needs to run on the ui thread because of ui components in it
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (_success) {
                            mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    mOld_Uploads.clear();
                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                        Old_Upload upload = postSnapshot.getValue(Old_Upload.class);
                                        upload.setKey(postSnapshot.getKey());
                                        mOld_Uploads.add(upload);
                                    }

                                    mAdapter.notifyDataSetChanged();

                                    mProgressCircle.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(Old_Products.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                    mProgressCircle.setVisibility(View.INVISIBLE);
                                }
                            });
                        } else {
                            File storageDir = Old_Products.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                            if(storageDir == null) throw new AssertionError("Cannot read " + Environment.DIRECTORY_PICTURES);
                            String path = storageDir.getAbsolutePath();
                            File directory = new File(path);
                            File[] files = directory.listFiles();
                            final List<File> products = new ArrayList<>();
                            boolean noneFound = true;
                            for (int i = 0; i < files.length; i++) {
                                String fileName = files[i].getName();
                                if(fileName.contains("Old_Products")) {
                                    noneFound = false;
                                    products.add(files[i]);
                                }
                            }

                            if(noneFound) {
                                Toast.makeText(getApplicationContext(),
                                        "No Internet and No Pictures!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                mOld_Uploads.clear();
                                for(File product : products){
                                    for(String productData : productDatas){
                                        if(productData.contains(product.getName())){
                                            Log.e("Hiram", "made it");
                                            //Old_Upload upload = Old_Upload();
                                            //mOld_Uploads.add(upload);
                                        }

                                    }
                                }
                                mAdapter.notifyDataSetChanged();
                            }

                            mProgressCircle.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onItemClick(int position) {
    }

    @Override
    public void onEditNameClick(final int position) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Old_Products.this);
        alertDialog.setTitle("Name");
        alertDialog.setMessage("Enter new name");

        final EditText input = new EditText(Old_Products.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input); // uncomment this line
        alertDialog.setIcon(R.drawable.stool_logo_orange);

        alertDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString();
                if (name.length() > 0) {
                    Old_Upload selectedItem = mOld_Uploads.get(position);
                    String selectedKey = selectedItem.getKey();
                    mDatabaseRef.child(selectedKey).child("name").setValue(name);
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

    @Override
    public void onEditDescriptionClick(final int position) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Old_Products.this);
        alertDialog.setTitle("Description");
        alertDialog.setMessage("Enter new description");

        final EditText input = new EditText(Old_Products.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input); // uncomment this line
        alertDialog.setIcon(R.drawable.stool_logo_orange);

        alertDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String description = input.getText().toString();
                if (description.length() > 0) {
                    Old_Upload selectedItem = mOld_Uploads.get(position);
                    String selectedKey = selectedItem.getKey();
                    mDatabaseRef.child(selectedKey).child("description").setValue(description);
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

    @Override
    public void onDeleteClick(int position) {
        Old_Upload selectedItem = mOld_Uploads.get(position);
        final String selectedKey = selectedItem.getKey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(Old_Products.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDBListener != null) {
            mDatabaseRef.removeEventListener(mDBListener);
        }
    }
}