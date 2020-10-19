package com.samboy.dmcc.profile.model;

import android.net.Uri;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.samboy.dmcc.BR;

import java.net.URI;

public class ProfileImage {

    private boolean isSuccess;
    private Uri imageUri;

    public ProfileImage() {
    }

    public ProfileImage(boolean isSuccess, Uri imageUri) {
        this.isSuccess = isSuccess;
        this.imageUri = imageUri;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
