package com.samboy.dmcc.certificate.repo;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.samboy.dmcc.auth.model.User;
import com.samboy.dmcc.database.Database;
import com.samboy.dmcc.jobs.model.Job;

public class CertificateRepository {
    private Database db;
    private FirebaseAuth firebaseAuth;

    private MutableLiveData<User> user = new MutableLiveData<>();
    private MutableLiveData<Job> job = new MutableLiveData<>();

    public CertificateRepository(Database db){
        this.db = db;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void fetchUserJob(){
        User u = db.userDao().getLoggedInUser();
        user.postValue(u);
        job.postValue(db.jobDao().getJob(u.getId()));
    }

    public void logOut(){
        firebaseAuth.signOut();
        db.userDao().logout(db.userDao().getLoggedInUser());
    }


    public MutableLiveData<Job> getJob() {
        return job;
    }

    public MutableLiveData<User> getUser() {
        return user;
    }
}
