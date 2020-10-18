package com.samboy.dmcc.profile.viewmodels;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.samboy.dmcc.constants.SC;
import com.samboy.dmcc.profile.repo.ProfileRepository;

import java.util.List;
import java.util.Locale;

public class ProfileViewModel extends ViewModel {
    private static String TAG = "ProfileViewModel";
    private ProfileRepository repository;
    private boolean isPermissionGrand = false;
    private FusedLocationProviderClient fusedLocation;
    private Geocoder geocoder;

    public MutableLiveData<String> address1 = new MutableLiveData<>();
    public MutableLiveData<String> address2 = new MutableLiveData<>();
    public MutableLiveData<String> address3 = new MutableLiveData<>();

    public ProfileViewModel(ProfileRepository repository) {
        this.repository = repository;

    }

    public void onProfileClick() {

    }

    public void onGpsClick(View view) {
        if (isPermissionGrand) {
            fusedLocation = LocationServices.getFusedLocationProviderClient(view.getContext());
            if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            fusedLocation.getLastLocation().addOnSuccessListener(location -> {
                if(location != null){
                    getAddress(view.getContext(),location);
                }
            });
        }else {
            Toast.makeText(view.getContext(), "Permission not Enabled", Toast.LENGTH_SHORT).show();
        }
    }

    private void getAddress(Context context, Location location){
        try{
            List<Address> addresses;
            geocoder = new Geocoder(context, Locale.getDefault());
            addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            address1.postValue(address);
            address2.postValue(city);
            address3.postValue(state+","+country);
        }catch (Exception e){
            Log.e(TAG, "getAddress: Exception"+e );
        }

    }

    public void onContinueClick() {

    }

    public void checkPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            this.isPermissionGrand = true;
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    SC.REQUEST_LOCATION_PERMISSION);
            isPermissionGrand = false;
        }
    }

    public void setPermissionGrand(boolean permissionGrand) {
        isPermissionGrand = permissionGrand;
    }


}
