package com.chopify.app.ui.products;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chopify.app.data.entities.Product;
import com.chopify.app.repositories.ProductRepository;

import java.util.List;

public class ProductsViewModel extends AndroidViewModel {

    private final ProductRepository productRepository;

    public ProductsViewModel(Application application){
        super(application);
        this.productRepository = new ProductRepository(application);
    }

    public LiveData<List<Product>> getProducts() {
        return this.productRepository.getAll();
    }

}