package com.chopify.app.ui.products;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chopify.app.databinding.FragmentProductsBinding;

import java.util.ArrayList;

public class ProductsFragment extends Fragment {

    private FragmentProductsBinding binding;
    private ProductAdapter adapter;
    private ProductsViewModel productsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        productsViewModel = new ViewModelProvider(this).get(ProductsViewModel.class);

        binding = FragmentProductsBinding.inflate(inflater, container, false);
        adapter = new ProductAdapter(getContext(), new ArrayList<>());
        binding.rvProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvProductos.setAdapter(adapter);

        productsViewModel.getProducts().observe(getViewLifecycleOwner(), products -> adapter.updateProducts(products));

        binding.etBuscarProductos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                productsViewModel.filterProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        productsViewModel.getFilteredProducts().observe(getViewLifecycleOwner(), products -> {
            Log.d("ProductsFragment", "Actualizando productos en RecyclerView: " + products.size());
            adapter.updateProducts(products);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}