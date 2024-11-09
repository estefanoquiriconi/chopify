package com.chopify.app.ui.orders;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ListItemHolder> {

    private List<Order> pedidos;


    @NonNull
    @Override
    public OrderAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ListItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private String fechaPedido;
        private String tituloPedido;
        private String descripcionPedido;

        public ListItemHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
