package com.chopify.app.ui.products;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.chopify.app.data.entities.Product;
import com.chopify.app.data.entities.Promotion;
import com.chopify.app.helpers.SessionManager;
import com.chopify.app.repositories.ProductPromotionRepository;
import com.chopify.app.repositories.ProductRepository;
import com.chopify.app.repositories.PromotionRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductsViewModel extends AndroidViewModel {

    private final ProductRepository productRepository;
    private final ProductPromotionRepository productPromotionRepository;
    private final LiveData<List<Product>> allProducts;
    private final MutableLiveData<List<Product>> filteredProducts = new MutableLiveData<>();
    private List<Product> productList = new ArrayList<>();
    private final SessionManager sessionManager;


    public ProductsViewModel(Application application) {
        super(application);
        this.productRepository = new ProductRepository(application);
        this.productPromotionRepository = new ProductPromotionRepository(application);

        this.sessionManager = new SessionManager(application.getApplicationContext());

        allProducts = productRepository.getAvailableByBusinessId(sessionManager.getBusiness().getId());
        allProducts.observeForever(products -> {
            Log.d("ProductsViewModel", "ProductsViewModel: productos cargados  size=" + products.size());
            productList = products;
            //applyDiscountsToProducts(productList);
            filteredProducts.setValue(productList);
        });
    }

    /*
    private void applyDiscountsToProducts(List<Product> products) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (Product product : products) {
            executorService.execute(() -> {
                List<Promotion> promotions = productPromotionRepository.getActivePromotionsSync(product.getId(), new Date());
                if (promotions != null && !promotions.isEmpty()) {
                    Promotion promotion = promotions.get(0);
                    product.setDiscount(promotion.getDiscount());
                    Log.d("ProductsViewModel", "applyDiscountsToProducts: producto=" + product.getName() + " promotion=" + promotion.getDiscount());
                } else {
                    product.setDiscount(0);
                    Log.d("ProductsViewModel", "applyDiscountsToProducts: producto=" + product.getName() + " promotion none");
                }

                // Actualiza LiveData en el hilo principal
                filteredProducts.postValue(productList);
            });
        }
        executorService.shutdown();
    }*/



    public LiveData<List<Product>> getFilteredProducts() {
        return filteredProducts;
    }

    public void filterProducts(String query) {
        Log.d("ProductsViewModel", "filterProducts: filtrado productos por query="+query);
        if (query == null || query.isEmpty()) {
            filteredProducts.setValue(productList);
        } else {
            List<Product> filteredList = new ArrayList<>();
            for (Product product : productList) {
                if (product.getName().toLowerCase().contains(query.toLowerCase())) {
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