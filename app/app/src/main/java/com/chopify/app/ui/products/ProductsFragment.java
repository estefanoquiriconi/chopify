package com.chopify.app.ui.products;

import android.os.Bundle;
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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ProductsViewModel productsViewModel = new ViewModelProvider(this).get(ProductsViewModel.class);

        binding = FragmentProductsBinding.inflate(inflater, container, false);
        adapter = new ProductAdapter(getContext(), new ArrayList<>());
        binding.rvProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvProductos.setAdapter(adapter);

        productsViewModel.getProducts().observe(getViewLifecycleOwner(), products -> adapter.updateProducts(products));

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}