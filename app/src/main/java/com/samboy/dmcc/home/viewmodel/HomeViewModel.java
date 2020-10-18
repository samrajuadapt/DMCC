package com.samboy.dmcc.home.viewmodel;

import android.util.Log;

import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.samboy.dmcc.auth.model.User;
import com.samboy.dmcc.constants.SC;
import com.samboy.dmcc.home.model.Country;
import com.samboy.dmcc.home.repo.HomeRepository;

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

    public HomeViewModel(HomeRepository repository){
        this.homeRepository = repository;
        mCountryList = homeRepository.getCountries();
        mRegionList = homeRepository.getRegions();
        mSateList = homeRepository.getStates();
        mTownList = homeRepository.getTowns();
        mJobList = homeRepository.getJobs();

    }

    public void fetchCountry(){
        homeRepository.fetchCountry();
    }

    public void fetchRegion(String country){
        homeRepository.fetchRegion(country);
    }

    public void fetchState(String region){
        region = SC.REGION+region.substring(region.length()-1);
       homeRepository.fetchState(region);
    }
    public void fetchTown(){
        homeRepository.fetchTown(SC.TOWN);
    }

    public void fetchJobs(){
        homeRepository.fetchJobs(SC.JOBS);
    }


    public String getUserName(){
        return "Hi "+homeRepository.getUser().getName();
    }


}
