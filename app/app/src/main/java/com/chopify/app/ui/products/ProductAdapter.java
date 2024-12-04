package com.chopify.app.ui.products;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
    private OnProductClickListener listener;


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
        holder.productName.setText(product.getName());
        holder.productDescription.setText(product.getDescription());
        if (product.getQuantity() != 0) {
            holder.productPrice.setText("$".concat(String.valueOf(product.getPrice()).concat(" x " + product.getQuantity())));
        } else {
            holder.productPrice.setText("$".concat(String.valueOf(product.getPrice())));
        }
        holder.productImage.setImageResource(getImage((int) product.getCategoryId()));
        holder.productDiscount.setText(String.valueOf(product.getId()));
        holder.addDiscount.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddDiscountClick(product);
            }
        });

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

    public void setOnProductClickListener(OnProductClickListener listener) {
        this.listener = listener;
    }

    public static class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView productImage;
        TextView productName;
        TextView productDescription;
        TextView productPrice;
        TextView productDiscount;
        ImageButton addDiscount;


        public ListItemHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imgProductItem);
            productName = itemView.findViewById(R.id.nameProductItem);
            productDescription = itemView.findViewById(R.id.descriptionProductItem);
            productPrice = itemView.findViewById(R.id.priceCantProductItem);
            productDiscount = itemView.findViewById(R.id.discountLabelProductItem);
            addDiscount = itemView.findViewById(R.id.addDsicountProduct);
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

    public interface OnProductClickListener {
        void onAddDiscountClick(Product product);
    }
}
