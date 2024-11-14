package com.chopify.app.ui.products;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.chopify.app.R;
import com.chopify.app.databinding.FragmentProductCreateEditBinding;

public class ProductCreateEditFragment extends Fragment {

    private ProductCreateEditViewModel mViewModel;
    private FragmentProductCreateEditBinding binding;

    public static ProductCreateEditFragment newInstance() {
        return new ProductCreateEditFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        binding = FragmentProductCreateEditBinding.inflate(inflater, container, false);

        exposedDropdown();

        return binding.getRoot();


    }

    @SuppressLint("ClickableViewAccessibility")
    private void exposedDropdown() {
        binding.exposedDropdown.setOnTouchListener((v, event) -> {
            binding.exposedDropdown.showDropDown();
            return true;
        });
     /*   binding.exposedDropdown.setOnItemClickListener((parent, view, position, id) -> {
            binding.categoryListLayout.setHint(null);
        });*/
        String[] items = new String[]{"Option 1", "Option 2", "Option 3", "Option 4"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, items);
        binding.exposedDropdown.setAdapter(adapter);
    }

}