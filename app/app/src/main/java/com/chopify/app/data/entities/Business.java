package com.chopify.app.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "businesses",
        foreignKeys = @ForeignKey(
                entity = Address.class,
                parentColumns = "id",
                childColumns = "address_id"
        ),
        indices = {@Index(value = {"email"}, unique = true),
                @Index(value = {"cuit"}, unique = true)}
)
public class Business {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String email;
    private String password;
    private String cuit;
    private String name;
    private String phone;
    @ColumnInfo(name = "address_id")
    private long addressId;
    private boolean status = true;

    public Business() {
    }

    public Business(String email, String password, String cuit, String name, String phone, long addressId) {
        this.email = email;
        this.password = password;
        this.cuit = cuit;
        this.name = name;
        this.phone = phone;
        this.addressId = addressId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}