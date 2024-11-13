package com.chopify.app.ui.orders;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chopify.app.R;
import com.chopify.app.data.entities.Order;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderHistoryFragment extends Fragment {

    private OrderHistoryViewModel orderHistoryViewModel;

    private RecyclerView recyclerView;
    private OrderAdapter adaptador;
    private List<Order> listaPedidos = new ArrayList<>();

    public static OrderHistoryFragment newInstance() {
        return new OrderHistoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);

        recyclerView = view.findViewById(R.id.rvPedidosPasados);

        // Inicializar el ViewModel
        orderHistoryViewModel = new ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(OrderHistoryViewModel.class);

        // Observar los datos del ViewModel
        orderHistoryViewModel.getBusinessHistoryOrders().observe(getViewLifecycleOwner(), orders -> {
            if (orders != null) {
                listaPedidos.clear();
                listaPedidos.addAll(orders);
                listaPedidos.removeIf(order -> (!order.getStatus().equals("Cancelado") && !order.getStatus().equals("Listo")));
                listaPedidos = listaPedidos.stream()
                        .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                        .collect(Collectors.toList());
                adaptador.notifyDataSetChanged();
                Log.d("OrderHistoryFragment", "onCreateView: se ha actualizado la lista de pedidos pasados");
            }
        });

        adaptador = new OrderAdapter(getContext(), listaPedidos, R.id.orderHistoryFragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptador);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        orderHistoryViewModel = new ViewModelProvider(this).get(OrderHistoryViewModel.class);
        // TODO: Use the ViewModel
    }

}