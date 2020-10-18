package com.samboy.dmcc.home.repo;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.samboy.dmcc.auth.model.User;
import com.samboy.dmcc.constants.SC;
import com.samboy.dmcc.database.Database;
import com.samboy.dmcc.home.model.Job;

import java.util.ArrayList;
import java.util.List;

public class HomeRepository {
    private static String TAG = "Home Repo";
    private Database db;
    private FirebaseFirestore fireStore;
    private DocumentReference docRef;

    private MutableLiveData<List<String>> mCountries = new MutableLiveData<>();
    private MutableLiveData<List<String>> mRegions = new MutableLiveData<>();
    private MutableLiveData<List<String>> mSate = new MutableLiveData<>();
    private MutableLiveData<List<String>> mTown = new MutableLiveData<>();
    private MutableLiveData<List<String>> mJob = new MutableLiveData<>();

    public HomeRepository(Database db){
        this.db = db;
        fireStore = FirebaseFirestore.getInstance();
    }


    public void fetchCountry(){
        List<String> mList = new ArrayList<>();
        docRef = fireStore.collection(SC.F_ARES).document(SC.F_COUNTRIES);
        docRef.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot snapshot = task.getResult();
                if(snapshot.exists()){
                    mList.add(SC.S_COUNTRY);
                    mList.addAll((List<String>)snapshot.get(SC.F_DATA));
                    mCountries.postValue(mList);;
                }
            }
        });
    }



    public void fetchRegion(String country){
        List<String> mList = new ArrayList<>();
        docRef = fireStore.collection(SC.F_ARES).document(country);
        docRef.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot snapshot = task.getResult();
                if(snapshot.exists()){
                    mList.add(SC.S_REGION);
                    mList.addAll((List<String>)snapshot.get(SC.F_DATA));
                    mRegions.postValue(mList);;
                }
            }
        });
    }

    public void fetchState(String region){
        List<String> mList = new ArrayList<>();
        docRef = fireStore.collection(SC.F_ARES).document(region);
        docRef.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot snapshot = task.getResult();
                if(snapshot.exists()){
                    mList.add(SC.S_STATE);
                    mList.addAll((List<String>)snapshot.get(SC.F_DATA));
                    mSate.postValue(mList);
                }
            }
        });
    }

    public void fetchTown(String data){
        List<String> mList = new ArrayList<>();
        docRef = fireStore.collection(SC.F_ARES).document(data);
        docRef.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot snapshot = task.getResult();
                if(snapshot.exists()){
                    mList.add(SC.S_TOWN);
                    mList.addAll((List<String>)snapshot.get(SC.F_DATA));
                    mTown.postValue(mList);
                }
            }
        });
    }
    public void fetchJobs(String data){
        List<String> mList = new ArrayList<>();
        docRef = fireStore.collection(SC.F_ARES).document(data);
        docRef.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot snapshot = task.getResult();
                if(snapshot.exists()){
                    mList.add(SC.S_JOB);
                    mList.addAll((List<String>)snapshot.get(SC.F_DATA));
                    mJob.postValue(mList);
                }
            }
        });
    }

    public MutableLiveData<List<String>> getCountries(){
        return mCountries;
    }
    public MutableLiveData<List<String>> getRegions(){
        return mRegions;
    }

    public MutableLiveData<List<String>> getStates(){
        return mSate;
    }

    public MutableLiveData<List<String>> getTowns(){
        return mTown;
    }
    public MutableLiveData<List<String>> getJobs(){
        return mJob;
    }

    public User getUser(){
        return db.userDao().getLoggedInUser();
    }
    public void saveJob(Job job){
        db.jobDao().addJob(job);
    }

}
