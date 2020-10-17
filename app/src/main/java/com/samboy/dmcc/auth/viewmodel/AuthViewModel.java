package com.samboy.dmcc.auth.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.samboy.dmcc.auth.listeners.OnLoginListeners;
import com.samboy.dmcc.auth.listeners.OnResisterListeners;
import com.samboy.dmcc.auth.model.AuthResponse;
import com.samboy.dmcc.auth.model.User;
import com.samboy.dmcc.auth.repo.AuthRepository;
import com.samboy.dmcc.auth.ui.RegisterActivity;

public class AuthViewModel extends ViewModel {
    private static String TAG = "AuthViewModel";
    private OnLoginListeners onLoginListeners;
    private OnResisterListeners onResisterListeners;

    private AuthRepository authRepository;

    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> mobile = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    public MutableLiveData<AuthResponse> authResponse;
    public MutableLiveData<Boolean> isProcessing = new MutableLiveData<>();

    public AuthViewModel(AuthRepository repository){
        this.authRepository = repository;
        authResponse = this.authRepository.getAuthResponse();
    }


    public void setOnResisterListeners(OnResisterListeners onResisterListeners) {
        this.onResisterListeners = onResisterListeners;
    }

    public void onLogin(){
        isProcessing.postValue(true);
        AuthResponse response = new AuthResponse();
        if(email.getValue()==null || password.getValue()==null){
            isProcessing.postValue(false);
            response.setMessage("Email or password is empty");
            authResponse.postValue(response);
            return;
        }
        authRepository.loginUser(email.getValue(),password.getValue());
    }

    public void onRegister(){
        onResisterListeners.onRegisterStart("Register Start");
        if(email.getValue() == null){
            onResisterListeners.onRegisterFail("Email is empty");
            return;
        }else if(password.getValue() == null){
            onResisterListeners.onRegisterFail("Password is empty");
            return;
        }else if (name.getValue() == null){
            onResisterListeners.onRegisterFail("Name is empty");
            return;
        }else if(mobile.getValue() == null){
            onResisterListeners.onRegisterFail("Mobile is empty");
            return;
        }
    }

    public void gotoRegister(View view){
        Context ctx  = view.getContext();
        ctx.startActivity(new Intent(ctx, RegisterActivity.class));
    }


}
