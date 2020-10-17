package com.samboy.dmcc.auth.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.samboy.dmcc.auth.model.AuthResponse;
import com.samboy.dmcc.auth.model.User;

public class AuthRepository {

    private FirebaseAuth firebaseAuth;
    private MutableLiveData<AuthResponse> authResponse;

    public AuthRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
        authResponse = new MutableLiveData<>();
    }

    public void registerUser(User user){

    }

    public void loginUser(String email, String password){
        AuthResponse res = new AuthResponse();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            Log.e("TAG", "loginUser: "+task.isSuccessful() );
            if(task.isSuccessful()){
                res.setSuccess(true);
                res.setMessage("Login Success");
               authResponse.postValue(res);
            }else {
                res.setSuccess(false);
                res.setMessage("Login Failed");
                authResponse.postValue(res);
            }
        });

    }

    public MutableLiveData<AuthResponse> getAuthResponse(){
        return authResponse;
    }
}
