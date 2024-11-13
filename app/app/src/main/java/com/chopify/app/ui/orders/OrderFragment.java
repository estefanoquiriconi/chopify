package com.chopify.app.ui.orders;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chopify.app.R;
import com.chopify.app.data.entities.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderFragment extends Fragment {


    private RecyclerView recyclerView;
    private OrderAdapter adaptador;
    private List<Order> listaPedidos = new ArrayList<>();
    private OrderViewModel orderViewModel;

    public static OrderFragment newInstance() {
        return new OrderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        // Inicializar el ViewModel
        orderViewModel = new ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(OrderViewModel.class);

        long businessId=2;

        orderViewModel.init(businessId);

        //recyclerview
        recyclerView = view.findViewById(R.id.rvPedidos);

        adaptador = new OrderAdapter(getContext(), listaPedidos, R.id.navigation_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptador);

        // Observar los datos del ViewModel
        orderViewModel.getBusinessOrdersWithCustomers().observe(getViewLifecycleOwner(), orders -> {
            if (orders != null) {
                listaPedidos.clear();
                listaPedidos.addAll(orders);
                listaPedidos.removeIf(order -> (!order.getStatus().equals("Activo")));
                listaPedidos = listaPedidos.stream()
                        .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                        .collect(Collectors.toList());
                adaptador.notifyDataSetChanged();
                Log.d("OrderFragment", "onCreateView: se ha actualizado la lista de pedidos con clientes. Size=" + listaPedidos.size());
            }
        });


        //nav to verTodosLosPedidos
        TextView textViewNavigate = view.findViewById(R.id.tvVerTodosPedidos);

        textViewNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_orderFragment_to_orderHistoryFragment);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        // TODO: Use the ViewModel
    }
}