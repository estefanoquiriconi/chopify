package com.chopify.app;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.chopify.app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);


        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            // Verifica los fragmentos donde vamos a renderizar el navigation menu
            if (destination.getId() == R.id.navigation_products ||
                    destination.getId() == R.id.navigation_order ||
                    destination.getId() == R.id.navigation_business) {
                binding.bottomNavigation.post(() -> binding.bottomNavigation.setVisibility(View.VISIBLE));
            } else {
                binding.bottomNavigation.setVisibility(View.GONE);
            }
        });

// Configura el BottomNavigationView con el NavController
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);



    }

}