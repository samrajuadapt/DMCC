package com.samboy.dmcc.auth.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.samboy.dmcc.auth.model.AuthResponse;
import com.samboy.dmcc.auth.model.User;
import com.samboy.dmcc.constants.SC;
import com.samboy.dmcc.database.Database;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class AuthRepository {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fireStore;
    private MutableLiveData<AuthResponse> authResponse;
    private DocumentReference docRef;
    private Database db;

    public AuthRepository(Database db) {
        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        authResponse = new MutableLiveData<>();
        this.db = db;
    }

    public void registerUser(User user){
        AuthResponse res = new AuthResponse();
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                user.setId(firebaseAuth.getCurrentUser().getUid());
                docRef = fireStore.collection(SC.F_USER).document(user.getId());
                Map<String,Object> userMap  = new HashMap<>();
                userMap.put(SC.C_NAME,user.getName());
                userMap.put(SC.C_EMAIL,user.getEmail());
                userMap.put(SC.C_MOBILE,user.getMobile());
                docRef.set(userMap).addOnCompleteListener(success -> {
                    if(success.isSuccessful()){
                        res.setSuccess(true);
                        res.setMessage("Register Success");
                        res.setUser(user);
                        authResponse.postValue(res);
                    }else {
                       sendRegisterFail(res);
                    }
                });
            }else {
                sendRegisterFail(res);
            }
        });
    }

    public void loginUser(String email, String password){
        AuthResponse res = new AuthResponse();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                docRef = fireStore.collection(SC.F_USER).document(firebaseAuth.getCurrentUser().getUid());
                docRef.get().addOnCompleteListener(userTask -> {
                    if(task.isSuccessful()){
                        DocumentSnapshot document = userTask.getResult();
                        if(document.exists()){
                            User user = new User();
                            user.setMobile(document.get("mobile").toString());
                            user.setName(document.get("name").toString());
                            user.setEmail(document.get("email").toString());
                            user.setId(firebaseAuth.getCurrentUser().getUid());
                            res.setSuccess(true);
                            res.setMessage("Login Success");
                            res.setUser(user);
                            authResponse.postValue(res);
                        }else {
                            sendLoginFail(res);
                        }
                    }else {
                        sendLoginFail(res);
                    }
                });
            }else {
                sendLoginFail(res);
            }
        });

    }

    public MutableLiveData<AuthResponse> getAuthResponse(){
        return authResponse;
    }
    public void saveUser(User user){
        db.userDao().login(user);
    }

    private void sendLoginFail(AuthResponse res){
        res.setSuccess(false);
        res.setMessage("Login Failed");
        authResponse.postValue(res);
    }
    private void sendRegisterFail(AuthResponse res){
        res.setSuccess(false);
        res.setMessage("Register Failed");
        authResponse.postValue(res);
    }

}
