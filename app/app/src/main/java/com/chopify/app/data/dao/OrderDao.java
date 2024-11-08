package com.chopify.app.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.chopify.app.data.entities.Order;

import java.util.List;

@Dao
public interface OrderDao {
    @Insert
    long insert(Order order);

    @Update
    void update(Order order);

    @Query("SELECT * FROM orders WHERE id = :id")
    Order getById(long id);

    @Query("SELECT * FROM orders WHERE business_id = :businessId ORDER BY order_date DESC")
    List<Order> getBusinessOrders(long businessId);

}
