package com.chopify.app.ui.products;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chopify.app.R;
import com.chopify.app.data.entities.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ListItemHolder> {

    private List<Product> products;
    private final Context context;

    public ProductAdapter(Context context, List<Product> products) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.fragment_rv_product_item, parent, false);
        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ListItemHolder holder, int position) {
        Product product = products.get(position);
        holder.productImage.setImageResource(getImage((int) product.getCategoryId()));
        holder.productName.setText(product.getName());
        holder.productDescription.setText(product.getDescription());
        holder.productPrice.setText(String.valueOf(product.getPrice()));
        holder.productDiscount.setText(String.valueOf(product.getId()));
    }

    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public static class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView productImage;
        TextView productName;
        TextView productDescription;
        TextView productPrice;
        TextView productDiscount;

        public ListItemHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imgProductItem);
            productName = itemView.findViewById(R.id.nameProductItem);
            productDescription = itemView.findViewById(R.id.descriptionProductItem);
            productPrice = itemView.findViewById(R.id.priceProductItem);
            productDiscount = itemView.findViewById(R.id.discountLabelProductItem);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public int getImage(int categoryId) {
        switch (categoryId) {
            case 1:
                return R.drawable.cerveza;
            case 2:
                return R.drawable.vino;
            case 3:
                return R.drawable.vodka;
            case 4:
                return R.drawable.fernet;
            default:
                return 0;
        }
    }
}
