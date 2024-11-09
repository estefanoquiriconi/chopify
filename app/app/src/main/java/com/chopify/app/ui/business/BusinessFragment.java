package com.chopify.app.ui.business;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.chopify.app.data.entities.Business;
import com.chopify.app.databinding.FragmentBusinessBinding;

public class BusinessFragment extends Fragment {

    private FragmentBusinessBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BusinessViewModel businessViewModel =
                new ViewModelProvider(this).get(BusinessViewModel.class);

        binding = FragmentBusinessBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        businessViewModel.getBusiness().observe(getViewLifecycleOwner(), businessWithAddress -> {
            Business businessData = businessWithAddress.getBusiness();

            binding.businessEmail.setText(businessData.getEmail());
            binding.businessName.setText(businessData.getName());
            binding.businessCuit.setText(businessData.getCuit());
            binding.businessPhone.setText(businessData.getPhone());
            binding.businessLocation.setText(businessWithAddress.getAddress());
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}