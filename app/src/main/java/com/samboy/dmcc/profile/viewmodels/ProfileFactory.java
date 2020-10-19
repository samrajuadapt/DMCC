package com.samboy.dmcc.profile.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.samboy.dmcc.profile.repo.ProfileRepository;

public class ProfileFactory extends ViewModelProvider.NewInstanceFactory {

    private ProfileRepository repository;

    public ProfileFactory(ProfileRepository repository){
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProfileViewModel(repository);
    }
}
