package com.samboy.dmcc.auth.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.samboy.dmcc.BaseActivity;
import com.samboy.dmcc.R;
import com.samboy.dmcc.auth.listeners.OnResisterListeners;
import com.samboy.dmcc.auth.model.User;
import com.samboy.dmcc.auth.repo.AuthRepository;
import com.samboy.dmcc.auth.viewmodel.AuthFactory;
import com.samboy.dmcc.auth.viewmodel.AuthViewModel;
import com.samboy.dmcc.databinding.ActivityRegisterBinding;

public class RegisterActivity extends BaseActivity implements OnResisterListeners {

    ActivityRegisterBinding binding;
    AuthViewModel authViewModel;
    AuthRepository authRepository;
    AuthFactory authFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = DataBindingUtil.setContentView(this,R.layout.activity_register);
        initViewModel();
    }

    private void initViewModel(){
        authRepository = new AuthRepository();
        authFactory = new AuthFactory(authRepository);
        authViewModel = new ViewModelProvider(this,authFactory).get(AuthViewModel.class);
        binding.setRegViewModel(authViewModel);
        authViewModel.setOnResisterListeners(this);
    }

    @Override
    public void onRegisterStart(String msg) {

    }

    @Override
    public void onRegisterSuccess(User user) {

    }

    @Override
    public void onRegisterFail(String msg) {
        toast(msg);
    }
}