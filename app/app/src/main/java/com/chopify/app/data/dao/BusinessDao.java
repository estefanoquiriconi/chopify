package com.chopify.app.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.chopify.app.data.entities.Business;
import com.chopify.app.data.relationships.BusinessWithAddress;

@Dao
public interface BusinessDao {

    @Insert
    long insert(Business business);

    @Update
    void update(Business business);

    @Delete
    void delete(Business business);

    @Query("SELECT * FROM businesses WHERE id = :id")
    LiveData<Business> getById(long id);

    @Query("SELECT b.*, a.address, a.latitude, a.longitude " +
            "FROM businesses b " +
            "JOIN addresses a ON b.address_id = a.id " +
            "WHERE b.id = :id AND b.status = 1 LIMIT 1")
    LiveData<BusinessWithAddress> getByIdWithAddress(long id);

    @Query("SELECT * FROM businesses WHERE email = :email LIMIT 1")
    LiveData<Business> findByEmail(String email);

    @Query("SELECT b.*, a.address, a.latitude, a.longitude " +
            "FROM businesses b " +
            "JOIN addresses a ON b.address_id = a.id " +
            "WHERE b.email = :email AND b.status = 1 LIMIT 1")
    LiveData<BusinessWithAddress> findByEmailWithAddress(String email);

}
