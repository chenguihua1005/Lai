package com.softtek.lai.module.picture.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

/**
 * Created by John on 2016/4/17.
 */
public class UploadImage implements Parcelable {

    private File image;
    private Bitmap bitmap;
    private Uri uri;

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public UploadImage() {
    }
    public UploadImage(File image, Bitmap bitmap) {
        this.image = image;
        this.bitmap = bitmap;
    }

    protected UploadImage(Parcel in) {
        uri = in.readParcelable(Uri.class.getClassLoader());
        image= (File) in.readSerializable();
    }

    public static final Creator<UploadImage> CREATOR = new Creator<UploadImage>() {
        @Override
        public UploadImage createFromParcel(Parcel in) {
            return new UploadImage(in);
        }

        @Override
        public UploadImage[] newArray(int size) {
            return new UploadImage[size];
        }
    };

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public String toString() {
        return "UploadImage{" +
                "image=" + image +
                ", bitmap=" + bitmap +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(uri, flags);
        dest.writeSerializable(image);
    }
}
