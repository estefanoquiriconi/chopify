package com.chopify.app.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_promotions",
        foreignKeys = {
                @ForeignKey(
                        entity = Product.class,
                        parentColumns = "id",
                        childColumns = "product_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Promotion.class,
                        parentColumns = "id",
                        childColumns = "promotion_id",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class ProductPromotion {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "product_id")
    private long productId;
    @ColumnInfo(name = "promotion_id")
    private long promotionId;

    public ProductPromotion(long productId, long promotionId) {
        this.productId = productId;
        this.promotionId = promotionId;
    }

    public ProductPromotion(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(long promotionId) {
        this.promotionId = promotionId;
    }
}