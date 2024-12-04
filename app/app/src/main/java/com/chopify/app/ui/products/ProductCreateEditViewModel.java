package com.chopify.app.ui.products;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.chopify.app.data.entities.Product;
import com.chopify.app.helpers.SessionManager;
import com.chopify.app.repositories.ProductRepository;


public class ProductCreateEditViewModel extends AndroidViewModel {

    private final ProductRepository productRepository;
    private final SessionManager sessionManager;

    public ProductCreateEditViewModel(Application application) {
        super(application);
        this.productRepository = new ProductRepository(application);
        this.sessionManager = new SessionManager(application.getApplicationContext());
    }

    public void createProduct(String name, String description, double price, int stock, int category) {
        Product product = new Product(name, description, price, stock, sessionManager.getBusiness().getId(), category);
        productRepository.insert(product);
    }

}