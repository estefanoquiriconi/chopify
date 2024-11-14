package com.chopify.app.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.chopify.app.data.entities.OrderDetail;

import java.util.List;

@Dao
public interface OrderDetailDao {
    @Insert
    long insert(OrderDetail detail);

    @Insert
    List<Long> insertAll(List<OrderDetail> details);

    @Query("SELECT * FROM order_details WHERE order_id = :orderId")
    LiveData<List<OrderDetail>> getOrderDetails(long orderId);

}
