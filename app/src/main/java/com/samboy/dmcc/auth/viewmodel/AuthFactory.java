package com.samboy.dmcc.auth.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.samboy.dmcc.auth.repo.AuthRepository;

public class AuthFactory extends ViewModelProvider.NewInstanceFactory {
    private AuthRepository repository;

    public AuthFactory(AuthRepository repository){
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AuthViewModel(repository);
    }
}
