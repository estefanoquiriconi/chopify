package com.chopify.app.ui.register;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chopify.app.R;
import com.chopify.app.databinding.ActivityBusinessRegistrationBinding;

import java.util.Objects;

public class BusinessRegistrationActivity extends AppCompatActivity {

    ActivityBusinessRegistrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityBusinessRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Intent intentAccount = getIntent();
        String email = intentAccount.getStringExtra("email");
        String password = intentAccount.getStringExtra("password");


        binding.btnContinue.setOnClickListener(v -> {
            String name = Objects.requireNonNull(binding.etBusinessName.getText()).toString().trim();
            String cuit = Objects.requireNonNull(binding.etBusinessCuit.getText()).toString().trim();
            String phone = Objects.requireNonNull(binding.etBusinessPhone.getText()).toString().trim();
            Intent intent = new Intent(this, AddressRegistrationActivity.class);

            intent.putExtra("email", email);
            intent.putExtra("password", password);
            intent.putExtra("name", name);
            intent.putExtra("cuit", cuit);
            intent.putExtra("phone", phone);

            startActivity(intent);
            finish();
        });
    }
}