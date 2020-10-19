package com.samboy.dmcc.jobs.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.samboy.dmcc.BaseActivity;
import com.samboy.dmcc.R;
import com.samboy.dmcc.database.Database;
import com.samboy.dmcc.databinding.ActivityJobBinding;
import com.samboy.dmcc.jobs.repo.JobRepository;
import com.samboy.dmcc.jobs.viewmodel.JobFactory;
import com.samboy.dmcc.jobs.viewmodel.JobViewModel;

public class JobActivity extends BaseActivity {

    ActivityJobBinding binding;
    JobViewModel jobViewModel;
    JobRepository jobRepository;
    JobFactory jobFactory;
    Database db;
    ArrayAdapter<String> countrySpinner;
    ArrayAdapter<String> regionSpinner;
    ArrayAdapter<String> stateSpinner;
    ArrayAdapter<String> townSpinner;
    ArrayAdapter<String> jobSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_job);
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
        jobRepository = new JobRepository(db);
        jobFactory = new JobFactory(jobRepository);
        jobViewModel = new ViewModelProvider(this, jobFactory).get(JobViewModel.class);
        binding.setJobViewModel(jobViewModel);
    }

    private void fetchCountry(){
        jobViewModel.fetchCountry();

    }

    private void observeCountry(){
        jobViewModel.mCountryList.observe(this, data->{
            countrySpinner  = new ArrayAdapter<String>(this,R.layout.custom_spinner,data);
            binding.setCountrySpinner(countrySpinner);
        });
    }

    private void observeRegion(){
        jobViewModel.mRegionList.observe(this, data->{
            regionSpinner  = new ArrayAdapter<String>(this,R.layout.custom_spinner,data);
            binding.setRegionSpinner(regionSpinner);
        });
    }
    private void observeState(){
        jobViewModel.mSateList.observe(this, data->{
            stateSpinner  = new ArrayAdapter<String>(this,R.layout.custom_spinner,data);
            binding.setStateSpinner(stateSpinner);
        });
    }
    private void observeTown(){
        jobViewModel.mTownList.observe(this, data->{
            townSpinner  = new ArrayAdapter<String>(this,R.layout.custom_spinner,data);
            binding.setTownSpinner(townSpinner);
        });
    }
    private void observeJob(){
        jobViewModel.mJobList.observe(this, data->{
            jobSpinner  = new ArrayAdapter<String>(this,R.layout.custom_spinner,data);
            binding.setJobSpinner(jobSpinner);
        });
    }

    private void selectedCountry(){
        jobViewModel.selectedCountry.observe(this, country->{
            jobViewModel.fetchRegion(country);
        });
    }

    private void selectedRegion(){
        jobViewModel.selectedRegion.observe(this, region->{
            jobViewModel.fetchState(region);
        });
    }
    private void selectedState(){
        jobViewModel.selectedState.observe(this, state->{
            jobViewModel.fetchTown();
        });
    }
    private void selectedTown(){
        jobViewModel.selectedTown.observe(this, town->{
            jobViewModel.fetchJobs();
        });
    }
    private void selectedJobs(){
        jobViewModel.selectedJob.observe(this, job->{

        });
    }

    @Override
    protected void onResume() {
        if(!jobViewModel.isLoggedIn()){
            finish();
        }
        super.onResume();
    }
}