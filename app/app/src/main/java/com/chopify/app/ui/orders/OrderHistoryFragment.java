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
import com.chopify.app.helpers.SessionManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrderHistoryFragment extends Fragment {

    private OrderViewModel orderHistoryViewModel;
    private RecyclerView recyclerView;
    private OrderAdapter adaptador;
    private List<Order> listaPedidos = new ArrayList<>();
    private SessionManager sessionManager;

    public static OrderHistoryFragment newInstance() {
        return new OrderHistoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);

        sessionManager = new SessionManager(requireContext());
        long businessId = 0;
        if (sessionManager.getBusiness() != null) {
            businessId = sessionManager.getBusiness().getId();
        }

        recyclerView = view.findViewById(R.id.rvPedidosPasados);

        // Inicializar el ViewModel
        orderHistoryViewModel = new ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(OrderViewModel.class);

        orderHistoryViewModel.init(businessId);

        adaptador = new OrderAdapter(getContext(), listaPedidos, R.id.orderHistoryFragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptador);

        orderHistoryViewModel.getBusinessOrdersWithDetails().observe(getViewLifecycleOwner(), orders -> {
            if (orders != null) {
                listaPedidos.clear();
                listaPedidos.addAll(orders.stream()
                        .filter(order -> order.getStatus().equals("Cancelado") || order.getStatus().equals("Listo"))
                        .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                        .collect(Collectors.toList()));
                adaptador.notifyDataSetChanged();
                Log.d("OrderHistoryFragment", "onCreateView: Lista actualizada con pedidos pasados. Size=" + listaPedidos.size());
            }
        });

        return view;
    }
}
