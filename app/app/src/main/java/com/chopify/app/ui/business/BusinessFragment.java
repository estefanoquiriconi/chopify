package com.chopify.app.ui.business;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.chopify.app.R;
import com.chopify.app.data.entities.Business;
import com.chopify.app.databinding.FragmentBusinessBinding;

public class BusinessFragment extends Fragment {

    private FragmentBusinessBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BusinessViewModel businessViewModel =
                new ViewModelProvider(this).get(BusinessViewModel.class);

        binding = FragmentBusinessBinding.inflate(inflater, container, false);
        businessViewModel.getBusiness().observe(getViewLifecycleOwner(), businessWithAddress -> {
            Business businessData = businessWithAddress.getBusiness();
            binding.businessEmail.setText(businessData.getEmail());
            binding.businessName.setText(businessData.getName());
            binding.businessCuit.setText(businessData.getCuit());
            binding.businessPhone.setText(businessData.getPhone());
            binding.businessLocation.setText(businessWithAddress.getAddress());
        });

        binding.sesionOut.setOnClickListener(v->{
            businessViewModel.onLogutListener();
        });
        businessViewModel.getLogOut().observe(getViewLifecycleOwner(), logOut ->{
            if(logOut){
                Navigation.findNavController(requireView()).navigate(R.id.mobile_navigation, null, new NavOptions.Builder().setPopUpTo(R.id.mobile_navigation, true).build());
            }
        } );
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}