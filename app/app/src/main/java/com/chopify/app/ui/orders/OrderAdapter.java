package com.chopify.app.ui.orders;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.chopify.app.R;
import com.chopify.app.data.entities.Order;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ListItemHolder> {

    private List<Order> pedidos;
    private Context context;
    private int fragmentId;

    public OrderAdapter(Context context, List<Order> listaPedidos, int fragmentId) {
        this.pedidos = listaPedidos;
        this.context = context;
        this.fragmentId = fragmentId;
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

        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM", Locale.getDefault());

        holder.fechaPedido.setText(formatoFecha.format(pedido.getOrderDate()));
        holder.horaPedido.setText(formatoHora.format(pedido.getOrderDate()));
        holder.tituloPedido.setText("destinado a " + pedido.getCustomerName());
        holder.descripcionPedido.setText("Cantidad de productos: ".concat(String.valueOf(pedido.getCantidadProductos()))); //para luego buscar
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
            if (fragmentId == R.id.navigation_order) {
                Order pedidoSeleccionado = pedidos.get(getAdapterPosition());

                Bundle bundle = new Bundle();
                bundle.putLong("orderId", pedidoSeleccionado.getId());

                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_orderFragment_to_orderDetailFragment, bundle);
            }
        }

    }
}
