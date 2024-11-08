package com.chopify.app.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.chopify.app.data.entities.Address;

import java.util.List;

@Dao
public interface AddressDao {
    @Insert
    long insert(Address address);

    @Update
    void update(Address address);

    @Delete
    void delete(Address address);

    @Query("SELECT * FROM addresses WHERE id = :id")
    Address getById(long id);

    @Query("SELECT * FROM addresses")
    List<Address> getAll();
}
