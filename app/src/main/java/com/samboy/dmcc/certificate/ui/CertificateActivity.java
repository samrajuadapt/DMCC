package com.samboy.dmcc.certificate.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.samboy.dmcc.BaseActivity;
import com.samboy.dmcc.R;
import com.samboy.dmcc.certificate.repo.CertificateRepository;
import com.samboy.dmcc.certificate.viewmodel.CertificateFactory;
import com.samboy.dmcc.certificate.viewmodel.CertificateViewModel;
import com.samboy.dmcc.database.Database;
import com.samboy.dmcc.databinding.ActivityCertificateBinding;

public class CertificateActivity extends BaseActivity {

    ActivityCertificateBinding binding;
    CertificateFactory factory;
    CertificateRepository repository;
    CertificateViewModel viewModel;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_certificate);
        binding.setLifecycleOwner(this);
        initViewModel();
        loadData();

    }

    private void initViewModel(){
        db = Database.getInstance(this);
        repository = new CertificateRepository(db);
        factory = new CertificateFactory(repository);
        viewModel = new ViewModelProvider(this,factory).get(CertificateViewModel.class);
        binding.setViewModel(viewModel);
        setSupportActionBar(binding.toolbar);
        setTitle("");
    }

    private void loadData(){
        viewModel.fetchUserJob();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuLogout){
            viewModel.logOut(this);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}