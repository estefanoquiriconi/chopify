package com.chopify.app.ui.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chopify.app.R;
import com.chopify.app.data.entities.Order;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ListItemHolder> {

    private List<Order> pedidos;
    private Context context;

    public OrderAdapter(Context context, List<Order> pedidos) {
        this.pedidos = pedidos;
        this.context = context;
    }

    @NonNull
    @Override
    public ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.fragment_rv_order_item, parent, false);
        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {
        Order pedido = pedidos.get(position);

        holder.fechaPedido.setText(pedido.getOrderDate().getDate() + "/" + pedido.getOrderDate().getDate());
        holder.horaPedido.setText(pedido.getOrderDate().getHours() + ":" + pedido.getOrderDate().getMinutes());
        holder.tituloPedido.setText(String.valueOf(pedido.getCustomerId())); //para luego buscar
        holder.descripcionPedido.setText(String.valueOf(pedido.getId())); //para luego buscar
        holder.estadoPedido.setText(pedido.getStatus());
    }

    @Override
    public int getItemCount() {
        return pedidos != null ? pedidos.size() : 0;
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView fechaPedido;
        private TextView horaPedido;
        private TextView tituloPedido;
        private TextView descripcionPedido;
        private TextView estadoPedido;

        public ListItemHolder(@NonNull View itemView) {
            super(itemView);

            fechaPedido = itemView.findViewById(R.id.dateOrderItem);
            horaPedido = itemView.findViewById(R.id.timeOrderItem);
            tituloPedido = itemView.findViewById(R.id.titleOrderItem);
            descripcionPedido = itemView.findViewById(R.id.descriptionOrderItem);
            estadoPedido = itemView.findViewById(R.id.statusOrderItem);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }
    }
}
