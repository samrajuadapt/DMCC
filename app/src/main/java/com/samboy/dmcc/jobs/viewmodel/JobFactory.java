package com.samboy.dmcc.jobs.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.samboy.dmcc.jobs.repo.JobRepository;

public class JobFactory extends ViewModelProvider.NewInstanceFactory {

    private JobRepository repository;

    public JobFactory(JobRepository repository){
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new JobViewModel(repository);
    }
}
