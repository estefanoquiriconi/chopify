package com.chopify.app.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.chopify.app.data.dao.PromotionDao;
import com.chopify.app.data.database.AppDataBase;
import com.chopify.app.data.entities.Promotion;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PromotionRepository {

    private final PromotionDao promotionDao;
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    public PromotionRepository(Application application) {
        AppDataBase appDataBase = AppDataBase.getInstance(application);
        promotionDao = appDataBase.promotionDao();
    }

    public void insert(Promotion promotion) {
        executor.execute(() -> promotionDao.insert(promotion));
    }

    public void update(Promotion promotion) {
        executor.execute(() -> promotionDao.update(promotion));
    }

    public void delete(Promotion promotion) {
        executor.execute(() -> promotionDao.delete(promotion));
    }

    public LiveData<Promotion> findById(long promotionId) {
        return promotionDao.findById(promotionId);
    }

    public LiveData<List<Promotion>> getActivePromotions(Date currentDate) {
        return promotionDao.getActivePromotions(currentDate);
    }
}