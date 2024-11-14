package com.chopify.app.ui.products;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chopify.app.data.entities.Product;
import com.chopify.app.repositories.ProductRepository;

import java.util.List;

public class ProductCreateEditViewModel extends AndroidViewModel {

    private final ProductRepository productRepository;

    private final MutableLiveData<List<Product>> products = new MutableLiveData<>();
    public ProductCreateEditViewModel(Application application){
        super(application);
        this.productRepository = new ProductRepository(application);
    }


    private LiveData<List<Product>> getProducts() {
        return this.products;
    }

}