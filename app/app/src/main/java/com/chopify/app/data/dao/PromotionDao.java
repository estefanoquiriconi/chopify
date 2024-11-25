package com.chopify.app.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.chopify.app.data.entities.Promotion;

import java.util.Date;
import java.util.List;

@Dao
public interface PromotionDao {
    @Insert
    long insert(Promotion promotion);

    @Update
    void update(Promotion promotion);

    @Delete
    void delete(Promotion promotion);

    @Query("SELECT * FROM promotions WHERE id = :id")
    LiveData<Promotion> findById(long id);

    @Query("SELECT * FROM promotions WHERE active = 1 AND start_date <= :currentDate AND end_date >= :currentDate")
    LiveData<List<Promotion>> getActivePromotions(Date currentDate);
}