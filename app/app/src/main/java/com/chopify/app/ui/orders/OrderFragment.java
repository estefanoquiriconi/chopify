package com.chopify.app.ui.orders;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chopify.app.R;
import com.chopify.app.data.entities.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderFragment extends Fragment {

    private OrderViewModel mViewModel;
    private RecyclerView recyclerView;
    private OrderAdapter adaptador;
    private List<Order> listaPedidos = new ArrayList<>();

    public static OrderFragment newInstance() {
        return new OrderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        //recyclerview
        recyclerView = view.findViewById(R.id.rvPedidos);

        obtenerListaPedidos();

        adaptador = new OrderAdapter(getContext(), listaPedidos, R.id.navigation_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptador);

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

    private void obtenerListaPedidos() {
        Order pedido = new Order();

        pedido.setId(1);
        pedido.setOrderDate(new Date());
        pedido.setStatus("Activo");
        listaPedidos.add(pedido);

        pedido = new Order();
        pedido.setId(2);
        pedido.setOrderDate(new Date());
        pedido.setStatus("Activo");
        listaPedidos.add(pedido);

        pedido = new Order();
        pedido.setId(3);
        pedido.setOrderDate(new Date());
        pedido.setStatus("Activo");
        listaPedidos.add(pedido);

        pedido = new Order();
        pedido.setId(4);
        pedido.setOrderDate(new Date());
        pedido.setStatus("Activo");
        listaPedidos.add(pedido);


        pedido = new Order();
        pedido.setId(5);
        pedido.setOrderDate(new Date());
        pedido.setStatus("Activo");
        listaPedidos.add(pedido);
    }

    @Override
    public void onResume() {
        super.onResume();
        listaPedidos.clear();
        this.obtenerListaPedidos();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        // TODO: Use the ViewModel
    }

}