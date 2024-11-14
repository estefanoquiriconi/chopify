package com.chopify.app.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.chopify.app.data.dao.ProductDao;
import com.chopify.app.data.database.AppDataBase;
import com.chopify.app.data.entities.Product;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductRepository {

    private final ProductDao productDao;

    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    public ProductRepository(Application application) {
        AppDataBase appDataBase = AppDataBase.getInstance(application);
        productDao = appDataBase.productDao();
    }

    public LiveData<Product> getById(long productId) {
        return productDao.getById(productId);
    }

    public LiveData<List<Product>> getAll() {
        return productDao.getAll();
    }

    public LiveData<List<Product>> getByCategory(long categoryId) {
        return productDao.getByCategory(categoryId);
    }

    public int updateStock(long productId, int quantity) {
        return productDao.updateStock(productId, quantity);
    }

    public void insert(Product product) {
        executor.execute(() -> productDao.insert(product));
    }

    public void update(Product product) {
        executor.execute(() -> productDao.update(product));
    }

    public void delete(Product product) {
        executor.execute(() -> productDao.delete(product));
    }

}
