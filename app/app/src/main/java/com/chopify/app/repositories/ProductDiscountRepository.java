package com.chopify.app.repositories;

import android.app.Application;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;


import androidx.lifecycle.LiveData;
import androidx.room.Transaction;

import com.chopify.app.data.dao.PromotionDao;
import com.chopify.app.data.database.AppDataBase;
import com.chopify.app.data.entities.ProductPromotion;
import com.chopify.app.data.entities.Promotion;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductDiscountRepository {

    private final PromotionDao promotionDao;
    private final ProductPromotionRepository promotionRepository;

    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    public ProductDiscountRepository(Application application) {
        AppDataBase appDataBase = AppDataBase.getInstance(application);
        this.promotionDao = appDataBase.promotionDao();
        this.promotionRepository = new ProductPromotionRepository(application);

    }
    public LiveData<Promotion> getByIdWithAddress(long id) {
        return promotionDao.findById(id);
    }


    @Transaction
    public void insert(Promotion promotion,long productId) {
        executor.execute(() ->{
        long promotionId = promotionDao.insert(promotion);

        // Inserta ProductPromotion solo si la promoción fue insertada correctamente
        if (promotionId > 0) {
            ProductPromotion productPromotion = new ProductPromotion();
            productPromotion.setProductId(productId);
            productPromotion.setPromotionId(promotionId);
            promotionRepository.insert(productPromotion);
        } else {
            throw new SQLiteConstraintException("Failed to insert promotion, foreign key constraint violation.");
        }});
    }

    public void update(Promotion promotion){
        executor.execute(() -> promotionDao.update(promotion));
    }

    public void delete(Promotion promotion){
        executor.execute(() ->promotionDao.delete(promotion));
    }
}
