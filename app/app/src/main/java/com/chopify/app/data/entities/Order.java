package com.chopify.app.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

import java.util.Date;

@Entity(tableName = "orders",
        foreignKeys = {
                @ForeignKey(
                        entity = Customer.class,
                        parentColumns = "id",
                        childColumns = "customer_id"
                ),
                @ForeignKey(
                        entity = Business.class,
                        parentColumns = "id",
                        childColumns = "business_id"
                ),
                @ForeignKey(
                        entity = Delivery.class,
                        parentColumns = "id",
                        childColumns = "delivery_id"
                )
        }
)
public class Order {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "customer_id")
    private long customerId;
    @ColumnInfo(name = "business_id")
    private long businessId;
    @ColumnInfo(name = "delivery_id")
    private long deliveryId;
    @ColumnInfo(name = "order_date")
    private Date orderDate;
    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(long businessId) {
        this.businessId = businessId;
    }

    public long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(long deliveryPersonId) {
        this.deliveryId = deliveryPersonId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}