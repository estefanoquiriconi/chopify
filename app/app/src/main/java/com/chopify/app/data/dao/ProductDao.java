package com.chopify.app.data.dao;

import androidx.lifecycle.LiveData;
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
    LiveData<Product> getById(long id);

    @Query("UPDATE products SET stock = stock - :quantity WHERE id = :productId AND stock >= :quantity")
    int updateStock(long productId, int quantity);

    @Query("SELECT * FROM products")
    LiveData<List<Product>> getAll();

    @Query("SELECT * FROM products WHERE category_id = :categoryId")
    LiveData<List<Product>> getAllByCategory(long categoryId);

    @Query("SELECT * FROM products WHERE business_id = :businessId AND stock > 0")
    LiveData<List<Product>> getAvailableProductsByBusinessId(long businessId);

    @Query("SELECT * FROM products WHERE category_id = :categoryId AND business_id = :businessId AND stock > 0")
    LiveData<List<Product>> getAvailableProductsByCategoryAndBusinessId(long categoryId, long businessId);


    @Query("SELECT * FROM products WHERE id IN (:productIds)")
    LiveData<List<Product>> getProductsByIds(List<Long> productIds);
}