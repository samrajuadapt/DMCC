package com.samboy.dmcc.jobs.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.samboy.dmcc.constants.SC;
import com.samboy.dmcc.jobs.model.Job;
import com.samboy.dmcc.jobs.repo.JobRepository;
import com.samboy.dmcc.profile.ui.ProfileActivity;

import java.util.List;

public class JobViewModel extends ViewModel {
    private static String TAG = "Home ViewModel";
    private JobRepository jobRepository;

    public MutableLiveData<List<String>> mCountryList = new MutableLiveData<>();
    public MutableLiveData<List<String>> mRegionList = new MutableLiveData<>();
    public MutableLiveData<List<String>> mSateList = new MutableLiveData<>();
    public MutableLiveData<List<String>> mTownList = new MutableLiveData<>();
    public MutableLiveData<List<String>> mJobList = new MutableLiveData<>();

    public MutableLiveData<String> selectedCountry = new MutableLiveData<>();
    public MutableLiveData<String> selectedRegion = new MutableLiveData<>();
    public MutableLiveData<String> selectedState = new MutableLiveData<>();
    public MutableLiveData<String> selectedTown = new MutableLiveData<>();
    public MutableLiveData<String> selectedJob = new MutableLiveData<>();

    public JobViewModel(JobRepository repository) {
        this.jobRepository = repository;
        mCountryList = jobRepository.getCountries();
        mRegionList = jobRepository.getRegions();
        mSateList = jobRepository.getStates();
        mTownList = jobRepository.getTowns();
        mJobList = jobRepository.getJobs();

    }

    public void fetchCountry() {
        jobRepository.fetchCountry();
    }

    public void fetchRegion(String country) {
        jobRepository.fetchRegion(country);
    }

    public void fetchState(String region) {
        region = SC.REGION + region.substring(region.length() - 1);
        jobRepository.fetchState(region);
    }

    public void fetchTown() {
        jobRepository.fetchTown(SC.TOWN);
    }

    public void fetchJobs() {
        jobRepository.fetchJobs(SC.JOBS);
    }


    public String getUserName() {
        return "Hi " + jobRepository.getUser().getName();
    }

    public void onContinueClick(View view) {
        Context context = view.getContext();
        if (selectedCountry.getValue().equals(SC.S_COUNTRY) || selectedCountry.getValue() == null) {
            Toast.makeText(context, SC.S_COUNTRY, Toast.LENGTH_SHORT).show();
            return;
        } else if (selectedRegion.getValue().equals(SC.S_REGION) || selectedRegion.getValue() == null) {
            Toast.makeText(context, SC.S_REGION, Toast.LENGTH_SHORT).show();
            return;
        } else if (selectedState.getValue().equals(SC.S_STATE) || selectedState.getValue() == null) {
            Toast.makeText(context, SC.S_STATE, Toast.LENGTH_SHORT).show();
            return;

        } else if (selectedTown.getValue().equals(SC.S_TOWN) || selectedTown.getValue() == null) {
            Toast.makeText(context, SC.S_TOWN, Toast.LENGTH_SHORT).show();
            return;
        } else if (selectedJob.getValue().equals(SC.S_JOB) || selectedJob.getValue() == null) {
            Toast.makeText(context, SC.S_JOB, Toast.LENGTH_SHORT).show();
            return;
        }
        String country = selectedCountry.getValue();
        String region = selectedRegion.getValue();
        String state = selectedState.getValue();
        String town = selectedTown.getValue();
        String job = selectedJob.getValue();
        Job jobModel = new Job(jobRepository.getUser().getId(),country,region,state,town,job);
        jobRepository.saveJob(jobModel);
        context.startActivity(new Intent(context, ProfileActivity.class));

    }

    public boolean isLoggedIn(){
        return jobRepository.isLoggedIn();
    }


}
