package com.samboy.dmcc.home.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.samboy.dmcc.BaseActivity;
import com.samboy.dmcc.R;
import com.samboy.dmcc.constants.SC;
import com.samboy.dmcc.database.Database;
import com.samboy.dmcc.databinding.ActivityHomeBinding;
import com.samboy.dmcc.home.model.Country;
import com.samboy.dmcc.home.model.Job;
import com.samboy.dmcc.home.model.Region;
import com.samboy.dmcc.home.model.State;
import com.samboy.dmcc.home.model.Town;
import com.samboy.dmcc.home.repo.HomeRepository;
import com.samboy.dmcc.home.viewmodel.HomeFactory;
import com.samboy.dmcc.home.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends BaseActivity {

    ActivityHomeBinding binding;
    HomeViewModel homeViewModel;
    HomeRepository homeRepository;
    HomeFactory homeFactory;
    Database db;
    ArrayAdapter<String> countrySpinner;
    ArrayAdapter<String> regionSpinner;
    ArrayAdapter<String> stateSpinner;
    ArrayAdapter<String> townSpinner;
    ArrayAdapter<String> jobSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home);
        initViewModels();


        fetchCountry();
        observeCountry();
        observeRegion();
        observeState();
        observeTown();
        observeJob();

        selectedCountry();
        selectedRegion();
        selectedState();
        selectedTown();
        selectedJobs();



    }
    private void initViewModels(){
        db = Database.getInstance(this);
        homeRepository = new HomeRepository(db);
        homeFactory = new HomeFactory(homeRepository);
        homeViewModel = new ViewModelProvider(this,homeFactory).get(HomeViewModel.class);
        binding.setHomeViewModel(homeViewModel);
    }

    private void fetchCountry(){
        homeViewModel.fetchCountry();

    }

    private void observeCountry(){
        homeViewModel.mCountryList.observe(this,data->{
            countrySpinner  = new ArrayAdapter<String>(this,R.layout.custom_spinner,data);
            binding.setCountrySpinner(countrySpinner);
        });
    }

    private void observeRegion(){
        homeViewModel.mRegionList.observe(this,data->{
            regionSpinner  = new ArrayAdapter<String>(this,R.layout.custom_spinner,data);
            binding.setRegionSpinner(regionSpinner);
        });
    }
    private void observeState(){
        homeViewModel.mSateList.observe(this,data->{
            stateSpinner  = new ArrayAdapter<String>(this,R.layout.custom_spinner,data);
            binding.setStateSpinner(stateSpinner);
        });
    }
    private void observeTown(){
        homeViewModel.mTownList.observe(this,data->{
            townSpinner  = new ArrayAdapter<String>(this,R.layout.custom_spinner,data);
            binding.setTownSpinner(townSpinner);
        });
    }
    private void observeJob(){
        homeViewModel.mJobList.observe(this,data->{
            jobSpinner  = new ArrayAdapter<String>(this,R.layout.custom_spinner,data);
            binding.setJobSpinner(jobSpinner);
        });
    }

    private void selectedCountry(){
        homeViewModel.selectedCountry.observe(this, country->{
            homeViewModel.fetchRegion(country);
        });
    }

    private void selectedRegion(){
        homeViewModel.selectedRegion.observe(this,region->{
            homeViewModel.fetchState(region);
        });
    }
    private void selectedState(){
        homeViewModel.selectedState.observe(this,state->{
            homeViewModel.fetchTown();
        });
    }
    private void selectedTown(){
        homeViewModel.selectedTown.observe(this,town->{
            homeViewModel.fetchJobs();
        });
    }
    private void selectedJobs(){
        homeViewModel.selectedJob.observe(this,job->{

        });
    }
}