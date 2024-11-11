package com.chopify.app.ui.register;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chopify.app.MainActivity;
import com.chopify.app.R;
import com.chopify.app.data.entities.Business;
import com.chopify.app.databinding.ActivityAddressRegistrationBinding;
import com.chopify.app.repositories.AddressRepository;
import com.chopify.app.repositories.BusinessRepository;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class AddressRegistrationActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 222;
    private ActivityAddressRegistrationBinding binding;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LatLng userLocation;

    private String email, password, name, cuit, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        setupMap();
        intentData();
        setupListeners();
    }

    private void initUI() {
        EdgeToEdge.enable(this);
        binding = ActivityAddressRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void intentData() {
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        name = intent.getStringExtra("name");
        cuit = intent.getStringExtra("cuit");
        phone = intent.getStringExtra("phone");
    }

    private void setupListeners() {
        binding.mapSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchLocation(query);
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

        binding.btnConfirm.setOnClickListener(v -> saveAddressAndBusiness());
    }

    private void searchLocation(String location) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addressList = geocoder.getFromLocationName(location, 1);
            if (addressList != null && !addressList.isEmpty()) {
                Address address = addressList.get(0);
                userLocation = new LatLng(address.getLatitude(), address.getLongitude());
                updateMap(userLocation, location);
            }
        } catch (IOException e) {
            Toast.makeText(this, "Error al buscar ubicación", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveAddressAndBusiness() {
        String addressText = Objects.requireNonNull(binding.etBusinessAddress.getText()).toString().trim();

        double latitude = userLocation.latitude;
        double longitude = userLocation.longitude;

        AddressRepository addressRepository = new AddressRepository(getApplication());
        long addressId = addressRepository.insert(new com.chopify.app.data.entities.Address(addressText, latitude, longitude));

        BusinessRepository businessRepository = new BusinessRepository(getApplication());
        businessRepository.insert(new Business(email, password, cuit, name, phone, addressId));

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setOnMapClickListener(latLng -> {
            userLocation = latLng;
            updateMap(userLocation, "Su ubicación seleccionada");
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
                        userLocation = location != null ? new LatLng(location.getLatitude(), location.getLongitude()) : defaultLatLng();
                        updateMap(userLocation, "Su ubicación actual");
                    });
        }
    }

    private LatLng defaultLatLng() {
        return new LatLng(-27.783370, -64.264183);
    }

    private void updateMap(LatLng latLng, String title) {
        googleMap.clear();
        googleMap.addMarker(new MarkerOptions().position(latLng).title(title));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            updateMap(defaultLatLng(), "Ubicación predeterminada");
        }
    }
}
