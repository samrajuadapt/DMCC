package com.samboy.dmcc.profile.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.samboy.dmcc.BaseActivity;
import com.samboy.dmcc.R;
import com.samboy.dmcc.constants.SC;
import com.samboy.dmcc.database.Database;
import com.samboy.dmcc.databinding.ActivityProfileBinding;
import com.samboy.dmcc.profile.listeners.OnProfileClick;
import com.samboy.dmcc.profile.model.ProfileImage;
import com.samboy.dmcc.profile.repo.ProfileRepository;
import com.samboy.dmcc.profile.viewmodels.ProfileFactory;
import com.samboy.dmcc.profile.viewmodels.ProfileViewModel;

public class ProfileActivity extends BaseActivity implements OnProfileClick {

    ActivityProfileBinding binding;
    ProfileRepository repository;
    ProfileFactory profileFactory;
    ProfileViewModel profileViewModel;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile);
        binding.setLifecycleOwner(this);
        initViewModel();
        init();
        observeUpload();
    }
    private void initViewModel(){
        db  = Database.getInstance(this);
        repository = new ProfileRepository(db);
        profileFactory = new ProfileFactory(repository);
        profileViewModel = new ViewModelProvider(this,profileFactory).get(ProfileViewModel.class);
        binding.setProfileViewModel(profileViewModel);
        profileViewModel.setOnProfileClick(this);
    }

    private void init(){
        profileViewModel.checkPermission(this);

    }
    private void observeUpload(){
        profileViewModel.imgProfile.observe(this,profile->{
            if(profile.isSuccess()){
                profileViewModel.gotoCertificate(this);
            }
        });
    }

    private void selectPhoto(){
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, SC.SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SC.SELECT_IMAGE) {
                Uri selectedImageURI = data.getData();
                ProfileImage profileImage = new ProfileImage(false,selectedImageURI);
                profileViewModel.imgProfile.postValue(profileImage);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        profileViewModel.setPermissionGrand(false);
        profileViewModel.setStoragePermissionGrand(false);
        switch (requestCode){
            case SC.REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    profileViewModel.setPermissionGrand(true);
                }
                break;
            case SC.REQUEST_STORAGE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    profileViewModel.setStoragePermissionGrand(true);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        if(!profileViewModel.isLoggedIn()){
            finish();
        }
        super.onResume();
    }

    @Override
    public void onProfileClick() {
        selectPhoto();
    }
}