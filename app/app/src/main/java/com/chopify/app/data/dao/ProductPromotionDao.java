package com.chopify.app.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.chopify.app.data.entities.ProductPromotion;
import com.chopify.app.data.entities.Promotion;

import java.util.Date;
import java.util.List;

@Dao
public interface ProductPromotionDao {
    @Insert
    long insert(ProductPromotion productPromotion);

    @Delete
    void delete(ProductPromotion productPromotion);

    @Query("DELETE FROM product_promotions WHERE product_id = :productId")
    void deleteAllForProduct(long productId);

    @Query("SELECT p.* FROM promotions p " +
            "JOIN product_promotions pp ON p.id = pp.product_id " +
            "WHERE pp.product_id = :productId AND p.active = 1 " +
            "AND p.start_date <= :currentDate AND p.end_date >= :currentDate")
    List<Promotion> getActivePromotionsForProduct(long productId, Date currentDate);
}