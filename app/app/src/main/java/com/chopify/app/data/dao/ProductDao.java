package com.chopify.app.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.chopify.app.data.entities.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    long insert(Product product);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);

    @Query("SELECT * FROM products WHERE id = :id")
    Product getById(long id);

    @Query("SELECT * FROM products WHERE business_id = :businessId")
    List<Product> getByBusiness(long businessId);

    @Query("UPDATE products SET stock = stock - :quantity WHERE id = :productId AND stock >= :quantity")
    int updateStock(long productId, int quantity);

    @Query("SELECT * FROM products WHERE category_id = :categoryId")
    List<Product> getByCategory(long categoryId);

    @Query("SELECT * FROM products WHERE stock > 0 AND business_id = :businessId")
    List<Product> getAvailableProducts(long businessId);
}