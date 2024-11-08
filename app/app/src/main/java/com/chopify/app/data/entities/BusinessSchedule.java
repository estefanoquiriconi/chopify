package com.chopify.app.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "business_schedules",
        foreignKeys = @ForeignKey(
                entity = Business.class,
                parentColumns = "id",
                childColumns = "business_id",
                onDelete = ForeignKey.CASCADE
        )
)
public class BusinessSchedule {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "business_id")
    private long businessId;
    @ColumnInfo(name = "day_of_week")
    private String dayOfWeek;
    @ColumnInfo(name = "opening_time")
    private String openingTime;
    @ColumnInfo(name = "closing_time")
    private String closingTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(long businessId) {
        this.businessId = businessId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }
}