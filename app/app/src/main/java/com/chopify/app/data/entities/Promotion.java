package com.chopify.app.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "promotions")
public class Promotion {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String description;
    private double discount;
    @ColumnInfo(name = "start_date")
    private Date startDate;
    @ColumnInfo(name = "end_date")
    private Date endDate;
    private boolean active = true;

    public Promotion(){
    }

    public Promotion(String name, String description, double discount, Date startDate, Date endDate, boolean active) {
        this.name = name;
        this.description = description;
        this.discount = discount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
