package com.chopify.app.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "products",
        foreignKeys = {
                @ForeignKey(
                        entity = Business.class,
                        parentColumns = "id",
                        childColumns = "business_id"
                ),
                @ForeignKey(
                        entity = Category.class,
                        parentColumns = "id",
                        childColumns = "category_id"
                )
        }
)
public class Product implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String description;
    private double price;
    private int stock;
    @ColumnInfo(name = "business_id")
    private long businessId;
    @ColumnInfo(name = "category_id")
    private long categoryId;
  
    @Ignore
    private int quantity;
    @Ignore
    private double discount;

    public Product() {
    }

    public Product(String name, String description, double price, int stock, long businessId, long categoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.businessId = businessId;
        this.categoryId = categoryId;
        this.quantity =0;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(long businessId) {
        this.businessId = businessId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
