package com.samboy.dmcc.certificate.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.samboy.dmcc.certificate.repo.CertificateRepository;

public class CertificateFactory extends ViewModelProvider.NewInstanceFactory{

    private CertificateRepository repository;

    public CertificateFactory(CertificateRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CertificateViewModel(repository);
    }
}
