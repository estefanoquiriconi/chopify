package com.chopify.app.ui.register;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chopify.app.MainActivity;
import com.chopify.app.R;
import com.chopify.app.databinding.ActivityAddressRegistrationBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class AddressRegistrationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActivityAddressRegistrationBinding binding;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 222;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;

    private Marker userMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddressRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        binding.mapSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = binding.mapSearch.getQuery().toString();
                List<Address> addressList;

                Geocoder geocoder = new Geocoder(AddressRegistrationActivity.this);
                try {
                    addressList = geocoder.getFromLocationName(location, 1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if (addressList != null && !addressList.isEmpty()) {
                    Address address = addressList.get(0);
                    googleMap.clear();
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        binding.floatingActionButtonMyLocation.setOnClickListener(v -> {
            googleMap.clear();
            getCurrentLocation();
        });

        binding.btnConfirm.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setOnMapClickListener(latLng -> {
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(latLng));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19));
        });
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            googleMap.addMarker(new MarkerOptions().position(userLocation).title("Tu ubicación"));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
                        } else {
                            LatLng defaultLocation = new LatLng(-27.783370, -64.264183);
                            googleMap.addMarker(new MarkerOptions().position(defaultLocation).title("Santiago del estero"));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15));
                        }
                    });
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                LatLng defaultLocation = new LatLng(-27.783370, -64.264183);
                googleMap.addMarker(new MarkerOptions().position(defaultLocation).title("Santiago del Estero"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15));
            }
        }
    }

}