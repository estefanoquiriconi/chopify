package com.chopify.app.ui.products;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chopify.app.data.entities.Product;
import com.chopify.app.helpers.SessionManager;
import com.chopify.app.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductsViewModel extends AndroidViewModel {

    private final ProductRepository productRepository;
    private final LiveData<List<Product>> allProducts;
    private final MutableLiveData<List<Product>> filteredProducts = new MutableLiveData<>();
    private List<Product> productList = new ArrayList<>();
    private final SessionManager sessionManager;

    public ProductsViewModel(Application application) {
        super(application);
        this.productRepository = new ProductRepository(application);
        this.sessionManager = new SessionManager(application.getApplicationContext());

        allProducts = productRepository.getAvailableByBusinessId(sessionManager.getBusiness().getId());
        allProducts.observeForever(products -> {
            Log.d("ProductsViewModel", "ProductsViewModel: productos cargados  size=" + products.size());
            productList = products;
            filteredProducts.setValue(productList);
        });
    }

    public LiveData<List<Product>> getFilteredProducts() {
        return filteredProducts;
    }

    public void filterProducts(String query) {
        Log.d("ProductsViewModel", "filterProducts: filtrado productos por query=" + query);
        if (query == null || query.isEmpty()) {
            filteredProducts.setValue(productList);
        } else {
            List<Product> filteredList = new ArrayList<>();
            for (Product product : productList) {
                if (product.getName().toLowerCase().contains(query.toLowerCase()) || product.getDescription().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(product);
                }
            }
            filteredProducts.setValue(filteredList);
        }
    }

    public LiveData<List<Product>> getProducts() {
        return this.productRepository.getAvailableByBusinessId(sessionManager.getBusiness().getId());
    }


}