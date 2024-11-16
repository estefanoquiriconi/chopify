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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chopify.app.R;
import com.chopify.app.data.entities.Order;
import com.chopify.app.helpers.SessionManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrderFragment extends Fragment {


    private RecyclerView recyclerView;
    private OrderAdapter adaptador;
    private List<Order> listaPedidos = new ArrayList<>();
    private OrderViewModel orderViewModel;
    private long businessId;

    public static OrderFragment newInstance() {
        return new OrderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        setearBusinessId();

        // Inicializar el ViewModel
        orderViewModel = new ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(OrderViewModel.class);

        orderViewModel.init(businessId);

        //recyclerview
        recyclerView = view.findViewById(R.id.rvPedidos);

        adaptador = new OrderAdapter(getContext(), listaPedidos, R.id.navigation_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptador);

        orderViewModel.getBusinessOrdersWithDetails().observe(getViewLifecycleOwner(), orders -> {
            if (orders != null) {
                listaPedidos.clear();
                listaPedidos.addAll(orders.stream()
                        .filter(order -> order.getStatus().equals("Activo"))
                        .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                        .collect(Collectors.toList()));
                adaptador.notifyDataSetChanged();
                Log.d("OrderFragment", "Pedidos actualizados. Size=" + listaPedidos.size());
            }
        });

        setearNavegacion(view);

        return view;
    }

    private void setearNavegacion(View view) {
        TextView textViewNavigate = view.findViewById(R.id.tvVerTodosPedidos);

        textViewNavigate.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_orderFragment_to_orderHistoryFragment);
        });
    }

    private void setearBusinessId() {
        SessionManager sessionManager = new SessionManager(requireContext());
        businessId = 0;
        if(sessionManager.getBusiness()!=null){
            businessId=sessionManager.getBusiness().getId();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (orderViewModel != null) {
            Log.d("OrderFragment", "onResume: actualizando pedidos");
            orderViewModel.refreshOrders();
        }
    }

}