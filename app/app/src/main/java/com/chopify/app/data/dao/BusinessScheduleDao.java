package com.chopify.app.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.chopify.app.data.entities.BusinessSchedule;

import java.util.List;

@Dao
public interface BusinessScheduleDao {
    @Insert
    long insert(BusinessSchedule schedule);

    @Insert
    List<Long> insertAll(List<BusinessSchedule> schedules);

    @Update
    void update(BusinessSchedule schedule);

    @Delete
    void delete(BusinessSchedule schedule);

    @Query("DELETE FROM business_schedules WHERE business_id = :businessId")
    void deleteAllForBusiness(long businessId);

    @Query("SELECT * FROM business_schedules WHERE business_id = :businessId")
    List<BusinessSchedule> getBusinessSchedules(long businessId);

    @Query("SELECT * FROM business_schedules WHERE business_id = :businessId AND day_of_week = :dayOfWeek")
    BusinessSchedule getScheduleForDay(long businessId, String dayOfWeek);
}