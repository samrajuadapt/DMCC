package com.samboy.dmcc.home.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.samboy.dmcc.auth.repo.AuthRepository;
import com.samboy.dmcc.auth.viewmodel.AuthViewModel;
import com.samboy.dmcc.home.repo.HomeRepository;

public class HomeFactory extends ViewModelProvider.NewInstanceFactory {

    private HomeRepository repository;

    public HomeFactory(HomeRepository repository){
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new HomeViewModel(repository);
    }
}
