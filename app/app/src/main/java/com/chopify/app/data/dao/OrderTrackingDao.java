package com.chopify.app.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.chopify.app.data.entities.OrderTracking;

import java.util.List;

@Dao
public interface OrderTrackingDao {
    @Insert
    long insert(OrderTracking tracking);

    @Query("SELECT * FROM order_tracking WHERE order_id = :orderId ORDER BY update_date DESC")
    List<OrderTracking> getOrderTracking(long orderId);

    @Query("SELECT * FROM order_tracking WHERE order_id = :orderId ORDER BY update_date DESC LIMIT 1")
    OrderTracking getLatestTracking(long orderId);
}