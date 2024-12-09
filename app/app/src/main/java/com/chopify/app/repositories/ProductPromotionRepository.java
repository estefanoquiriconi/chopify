package com.chopify.app.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.chopify.app.data.dao.ProductPromotionDao;
import com.chopify.app.data.database.AppDataBase;
import com.chopify.app.data.entities.ProductPromotion;
import com.chopify.app.data.entities.Promotion;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductPromotionRepository {

    private final ProductPromotionDao productPromotionDao;
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    public ProductPromotionRepository(Application application) {
        AppDataBase appDataBase = AppDataBase.getInstance(application);
        productPromotionDao = appDataBase.productPromotionDao();
    }

    public void insert(ProductPromotion productPromotion) {
        executor.execute(() -> productPromotionDao.insert(productPromotion));
    }

    public void delete(ProductPromotion productPromotion) {
        executor.execute(() -> productPromotionDao.delete(productPromotion));
    }

    public void deleteAllForProduct(long productId) {
        executor.execute(() -> productPromotionDao.deleteAllForProduct(productId));
    }
}
