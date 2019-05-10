package com.example.barstoolprinting;

import com.google.firebase.database.Exclude;

public class Upload {
    private String mName;
    private String mImageUrl;
    private String mDescription;
    private String mKey;

    public Upload() {
        //empty constructor needed
    }

    public Upload(String name, String imageUrl, String description) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        if (description.trim().equals("")) {
            description = "No Description";
        }

        mName = name;
        mImageUrl = imageUrl;
        mDescription = description;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}
