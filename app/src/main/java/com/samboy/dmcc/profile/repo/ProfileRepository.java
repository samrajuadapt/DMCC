package com.samboy.dmcc.profile.repo;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.samboy.dmcc.auth.model.User;
import com.samboy.dmcc.database.Database;
import com.samboy.dmcc.profile.model.ProfileImage;

public class ProfileRepository {
    private static String TAG = "Profile Repo";
    FirebaseStorage storage;
    StorageReference storageRef;
    private Database db;

    private MutableLiveData<ProfileImage> imgProfile = new MutableLiveData<>();


    public ProfileRepository(Database db){
        this.db = db;
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }

    public void uploadImage(Uri uri){
        ProfileImage profileImage = new ProfileImage();
        StorageReference proRef = storageRef.child("images/"+uri.getLastPathSegment());
        UploadTask uploadTask = proRef.putFile(uri);
        Task<Uri> uriTask = uploadTask.continueWithTask(task -> {
            if(!task.isSuccessful()){
                profileImage.setSuccess(false);
                profileImage.setImageUri(null);
                imgProfile.postValue(profileImage);
                throw task.getException();
            }
            return proRef.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                profileImage.setSuccess(true);
                profileImage.setImageUri(downloadUri);
                imgProfile.postValue(profileImage);
            } else {
                Log.e(TAG, "uploadImage: Failed" );
            }
        }).addOnFailureListener(e -> {
            profileImage.setSuccess(false);
            profileImage.setImageUri(null);
            imgProfile.postValue(profileImage);
            Log.e(TAG, "uploadImage: Failed"+e );
        });
    }

    public void updateUserImage(){
        User user = db.userDao().getLoggedInUser();
        user.setImage(imgProfile.getValue().getImageUri().toString());
        db.userDao().update(user);
    }

    public boolean isLoggedIn(){
        return db.userDao().getLoggedInUser() != null;
    }


    public MutableLiveData<ProfileImage> getImgProfile(){
        return imgProfile;
    }
}
