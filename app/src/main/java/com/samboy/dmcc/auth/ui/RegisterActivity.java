package com.samboy.dmcc.auth.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.samboy.dmcc.BaseActivity;
import com.samboy.dmcc.R;

import com.samboy.dmcc.auth.repo.AuthRepository;
import com.samboy.dmcc.auth.viewmodel.AuthFactory;
import com.samboy.dmcc.auth.viewmodel.AuthViewModel;
import com.samboy.dmcc.database.Database;
import com.samboy.dmcc.databinding.ActivityRegisterBinding;

public class RegisterActivity extends BaseActivity{

    ActivityRegisterBinding binding;
    AuthViewModel authViewModel;
    AuthRepository authRepository;
    AuthFactory authFactory;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = DataBindingUtil.setContentView(this,R.layout.activity_register);
        initViewModel();
        observeProcessing();
        observeResponse();
    }

    private void initViewModel(){
        db = Database.getInstance(this);
        authRepository = new AuthRepository(db);
        authFactory = new AuthFactory(authRepository);
        authViewModel = new ViewModelProvider(this,authFactory).get(AuthViewModel.class);
        binding.setRegViewModel(authViewModel);

    }

    private void observeProcessing(){
        authViewModel.isProcessing.observe(this,isShow->{

        });
    }

    private void observeResponse(){
        authViewModel.authResponse.observe(this,authResponse -> {
            if(authResponse.isSuccess()){
                toast(authResponse.getMessage());
                authViewModel.saveUser(authResponse.getUser());
                authViewModel.gotoHome(this);
            }else {
                toast(authResponse.getMessage());
            }
        });
    }

}