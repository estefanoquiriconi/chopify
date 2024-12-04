package com.chopify.app.ui.products;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.chopify.app.databinding.FragmentProductCreateEditBinding;

import java.util.Objects;

public class ProductCreateEditFragment extends Fragment {
    private static final String[] PRODUCT_CATEGORIES = {"Cerveza", "Vino", "Vodka", "Fernet"};
    private ProductCreateEditViewModel viewModel;
    private FragmentProductCreateEditBinding binding;
    private int selectedCategoryId = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProductCreateEditBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ProductCreateEditViewModel.class);

        setupCategoryDropdown();
        binding.buttonSave.setOnClickListener(v -> saveProduct());
        binding.buttonCancel.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupCategoryDropdown() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                PRODUCT_CATEGORIES
        );

        binding.exposedDropdown.setAdapter(adapter);
        binding.exposedDropdown.setOnTouchListener((v, event) -> {
            binding.exposedDropdown.showDropDown();
            return true;
        });

        binding.exposedDropdown.setOnItemClickListener((parent, view, position, id) ->
                selectedCategoryId = position + 1
        );
    }

    private void saveProduct() {
        if (!validateFields()) {
            return;
        }
        String name = Objects.requireNonNull(binding.productName.getText()).toString().trim();
        String description = Objects.requireNonNull(binding.productDescription.getText()).toString().trim();
        double price = Double.parseDouble(Objects.requireNonNull(binding.productPrice.getText()).toString().trim());
        int stock = Integer.parseInt(Objects.requireNonNull(binding.productStock.getText()).toString().trim());

        viewModel.createProduct(name, description, price, stock, selectedCategoryId);
        showMessage("Producto guardado exitosamente");
        clearFields();
        binding.productName.requestFocus();
    }

    private boolean validateFields() {
        if (isFieldEmpty(binding.productName.getText(), "Debes ingresar un nombre")) return false;
        if (isFieldEmpty(binding.productDescription.getText(), "Debes ingresar una descripción"))
            return false;
        if (isFieldEmpty(binding.productPrice.getText(), "Debes ingresar el precio")) return false;
        if (isFieldEmpty(binding.productStock.getText(), "Debes ingresar el stock")) return false;

        if (selectedCategoryId == 0) {
            showMessage("Debes seleccionar una categoría");
            return false;
        }
        return true;
    }

    private boolean isFieldEmpty(Editable field, String errorMessage) {
        if (field == null || field.toString().trim().isEmpty()) {
            showMessage(errorMessage);
            return true;
        }
        return false;
    }

    private void clearFields() {
        binding.productName.setText("");
        binding.productDescription.setText("");
        binding.productPrice.setText("");
        binding.productStock.setText("");
        binding.exposedDropdown.setText("");
        selectedCategoryId = 0;
    }

    private void showMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

}