package com.chopify.app.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.chopify.app.data.entities.Delivery;

@Dao
public interface DeliveryDao {

    @Insert
    long insert(Delivery delivery);

    @Update
    void update(Delivery delivery);

    @Delete
    void delete(Delivery delivery);

    @Query("SELECT * FROM delivery WHERE id = :id")
    Delivery getById(long id);

}
