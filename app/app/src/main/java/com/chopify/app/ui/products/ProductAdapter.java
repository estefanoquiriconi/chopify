package com.chopify.app.ui.products;

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

    private List<Product> productos;
    private Context context;

    public ProductAdapter(Context context, List<Product> productos) {
        this.productos = productos;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.fragment_rv_product_item, parent, false);
        return new ProductAdapter.ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ListItemHolder holder, int position) {
        Product producto = productos.get(position);

        holder.nombreProducto.setText(producto.getName());
        holder.descripcionProducto.setText(producto.getDescription());
        holder.precioProducto.setText(String.valueOf(producto.getPrice()));
        holder.descuentoProducto.setText(String.valueOf(producto.getId())); //para luego buscar
    }

    @Override
    public int getItemCount() {
        return productos != null ? productos.size() : 0;
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imagenProducto;
        TextView nombreProducto;
        TextView descripcionProducto;
        TextView precioProducto;
        TextView descuentoProducto;


        public ListItemHolder(@NonNull View itemView) {
            super(itemView);

             imagenProducto = itemView.findViewById(R.id.imgProductItem);
             nombreProducto= itemView.findViewById(R.id.nameProductItem);
             descripcionProducto= itemView.findViewById(R.id.descriptionProductItem);
             precioProducto= itemView.findViewById(R.id.priceCantProductItem);
             descuentoProducto= itemView.findViewById(R.id.discountLabelProductItem);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
