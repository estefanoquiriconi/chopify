package com.chopify.app.data.dao;

import androidx.lifecycle.LiveData;
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
    LiveData<Order> getById(long id);

    @Query("SELECT * FROM orders WHERE business_id = :businessId ORDER BY order_date DESC")
    LiveData<List<Order>> getBusinessOrders(long businessId);

    @Query("SELECT * FROM orders WHERE status = :status ORDER BY order_date DESC")
    LiveData<List<Order>> getOrdersByStatus(String status);

    @Query("SELECT * FROM orders WHERE business_id = :businessId AND status = :status ORDER BY order_date DESC")
    LiveData<List<Order>> getBusinessOrdersByStatus(long businessId, String status);

    @Query("UPDATE orders SET status = :newStatus WHERE id = :orderId")
    void updateOrderStatus(long orderId, String newStatus);
}
