package com.samboy.dmcc.auth.viewmodel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.samboy.dmcc.auth.model.AuthResponse;
import com.samboy.dmcc.auth.model.User;
import com.samboy.dmcc.auth.repo.AuthRepository;
import com.samboy.dmcc.auth.ui.RegisterActivity;
import com.samboy.dmcc.jobs.ui.JobActivity;

public class AuthViewModel extends ViewModel {
    private static String TAG = "AuthViewModel";

    private ProgressDialog pDialog;
    private AuthRepository authRepository;
    private AuthResponse response;

    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> mobile = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    public MutableLiveData<AuthResponse> authResponse;
    public MutableLiveData<Boolean> isProcessing = new MutableLiveData<>();

    public AuthViewModel(AuthRepository repository){
        this.authRepository = repository;
        authResponse = this.authRepository.getAuthResponse();
        response = new AuthResponse();
    }


    public void onLogin(View view){
        isProcessing.postValue(true);
        if(email.getValue()==null || password.getValue()==null){
            isProcessing.postValue(false);
            response.setMessage("Email or password is empty");
            authResponse.postValue(response);
            return;
        }
        pDialog = new ProgressDialog(view.getContext());
        pDialog.setMessage("Signing in");
        pDialog.show();
        authRepository.loginUser(email.getValue(),password.getValue());
    }

    public void onRegister(View view){
        isProcessing.postValue(true);
        if(email.getValue() == null){
            isProcessing.postValue(false);
            response.setMessage("Email is empty");
            authResponse.postValue(response);
            return;
        }else if(password.getValue() == null){
            isProcessing.postValue(false);
            response.setMessage("Password is empty");
            authResponse.postValue(response);
            return;
        }else if(password.getValue() != null && password.getValue().length()<6){
            isProcessing.postValue(false);
            response.setMessage("Password must have minimum 6 character");
            authResponse.postValue(response);
        }
        else if (name.getValue() == null){
            isProcessing.postValue(false);
            response.setMessage("Name is empty");
            authResponse.postValue(response);
            return;
        }else if(mobile.getValue() == null){
            isProcessing.postValue(false);
            response.setMessage("Mobile is empty");
            authResponse.postValue(response);
            return;
        }
        pDialog = new ProgressDialog(view.getContext());
        pDialog.setMessage("Registering");
        pDialog.show();
        User user  = new User("",name.getValue(),email.getValue(),mobile.getValue(),password.getValue());
        authRepository.registerUser(user);

    }

    public void gotoRegister(View view){
        Context ctx  = view.getContext();
        ctx.startActivity(new Intent(ctx, RegisterActivity.class));
    }

    public void gotoHome(Context ctx){
        pDialog.dismiss();
        ctx.startActivity(new Intent(ctx, JobActivity.class));
    }
    public void dismiss(){
        pDialog.dismiss();
    }

    public void saveUser(User user){
        authRepository.saveUser(user);
    }


}
