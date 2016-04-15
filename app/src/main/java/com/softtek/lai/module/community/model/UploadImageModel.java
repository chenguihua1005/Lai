package com.softtek.lai.module.community.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * Created by jerry.guan on 4/15/2016.
 */
public class UploadImageModel{

    private List<File> images;

    public List<File> getImages() {
        return images;
    }

    public void setImages(List<File> images) {
        this.images = images;
    }

    public UploadImageModel(List<File> images) {
        this.images = images;
    }

    public UploadImageModel() {
    }
}
