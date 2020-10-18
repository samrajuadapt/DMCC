package com.samboy.dmcc.home.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.samboy.dmcc.constants.SC;
import com.samboy.dmcc.home.model.Job;
import com.samboy.dmcc.home.repo.HomeRepository;
import com.samboy.dmcc.profile.ui.ProfileActivity;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private static String TAG = "Home ViewModel";
    private HomeRepository homeRepository;

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

    public HomeViewModel(HomeRepository repository) {
        this.homeRepository = repository;
        mCountryList = homeRepository.getCountries();
        mRegionList = homeRepository.getRegions();
        mSateList = homeRepository.getStates();
        mTownList = homeRepository.getTowns();
        mJobList = homeRepository.getJobs();

    }

    public void fetchCountry() {
        homeRepository.fetchCountry();
    }

    public void fetchRegion(String country) {
        homeRepository.fetchRegion(country);
    }

    public void fetchState(String region) {
        region = SC.REGION + region.substring(region.length() - 1);
        homeRepository.fetchState(region);
    }

    public void fetchTown() {
        homeRepository.fetchTown(SC.TOWN);
    }

    public void fetchJobs() {
        homeRepository.fetchJobs(SC.JOBS);
    }


    public String getUserName() {
        return "Hi " + homeRepository.getUser().getName();
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
        Job jobModel = new Job(homeRepository.getUser().getId(),country,region,state,town,job);
        homeRepository.saveJob(jobModel);
        context.startActivity(new Intent(context, ProfileActivity.class));

    }


}
