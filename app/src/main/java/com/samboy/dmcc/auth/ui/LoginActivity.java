package com.samboy.dmcc.auth.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.samboy.dmcc.BaseActivity;
import com.samboy.dmcc.R;
import com.samboy.dmcc.auth.listeners.OnLoginListeners;
import com.samboy.dmcc.auth.model.User;
import com.samboy.dmcc.auth.repo.AuthRepository;
import com.samboy.dmcc.auth.viewmodel.AuthFactory;
import com.samboy.dmcc.auth.viewmodel.AuthViewModel;
import com.samboy.dmcc.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity {

    ActivityLoginBinding binding;
    AuthViewModel authViewModel;
    AuthRepository authRepository;
    AuthFactory authFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        initViewModel();
        observeProcess();
        observeLogin();
    }
    private void initViewModel(){
        authRepository = new AuthRepository();
        authFactory  = new AuthFactory(authRepository);
        authViewModel = new ViewModelProvider(this,authFactory).get(AuthViewModel.class);
        binding.setLoginViewModel(authViewModel);
    }

    private void observeProcess(){
        authViewModel.isProcessing.observe(this,isShow->{

        });
    }

    private void observeLogin(){
        authViewModel.authResponse.observe(this,response->{

            if (response.isSuccess()){
            }else {
                toast(response.getMessage());
            }
        });
    }


}