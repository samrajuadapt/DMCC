package com.samboy.dmcc.profile.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.samboy.dmcc.BaseActivity;
import com.samboy.dmcc.R;
import com.samboy.dmcc.constants.SC;
import com.samboy.dmcc.databinding.ActivityProfileBinding;
import com.samboy.dmcc.profile.repo.ProfileRepository;
import com.samboy.dmcc.profile.viewmodels.ProfileFactory;
import com.samboy.dmcc.profile.viewmodels.ProfileViewModel;

public class ProfileActivity extends BaseActivity {

    ActivityProfileBinding binding;
    ProfileRepository repository;
    ProfileFactory profileFactory;
    ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile);
        binding.setLifecycleOwner(this);
        initViewModel();
        init();
    }
    private void initViewModel(){
        repository = new ProfileRepository();
        profileFactory = new ProfileFactory(repository);
        profileViewModel = new ViewModelProvider(this,profileFactory).get(ProfileViewModel.class);
        binding.setProfileViewModel(profileViewModel);
    }

    private void init(){
        profileViewModel.checkPermission(this);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        profileViewModel.setPermissionGrand(false);
        switch (requestCode){
            case SC.REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    profileViewModel.setPermissionGrand(true);
                }
                break;
        }
    }

}