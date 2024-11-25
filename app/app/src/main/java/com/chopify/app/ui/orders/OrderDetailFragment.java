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
import android.widget.Button;
import android.widget.TextView;

import com.chopify.app.R;
import com.chopify.app.data.entities.Product;
import com.chopify.app.ui.products.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailFragment extends Fragment {

    private OrderDetailViewModel orderDetailViewModel;
    private RecyclerView recyclerView;
    private ProductAdapter adaptador;
    private final List<Product> listaProductos = new ArrayList<>();
    private Long orderId;
    private Button bttnAceptarPedido;
    private Button bttnCancelarPedido;
    private View rootView;

    public static OrderDetailFragment newInstance() {
        return new OrderDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_order_detail, container, false);

        bttnAceptarPedido = rootView.findViewById(R.id.bttnAceptarPedido);
        bttnCancelarPedido = rootView.findViewById(R.id.bttnCancelarPedido);

        orderDetailViewModel = new ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(OrderDetailViewModel.class);

        if (getArguments() != null) {
            orderId = getArguments().getLong("orderId");
            Log.d("OrderDetail", "onCreateView: el id del pedido es " + orderId);
        }

        //recycler view
        recyclerView = rootView.findViewById(R.id.rvProductosDelPedido);
        adaptador = new ProductAdapter(getContext(), listaProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptador);

        orderDetailViewModel.getProductsByOrderId(this.orderId).observe(getViewLifecycleOwner(), productos -> {
            if (productos != null) {
                listaProductos.clear();
                listaProductos.addAll(productos);
                adaptador.notifyDataSetChanged();
            }
        });

        orderDetailViewModel.getTotalPedido(orderId).observe(getViewLifecycleOwner(), total -> {
            TextView tvTotalPedido = rootView.findViewById(R.id.tvTotalPedido);
            tvTotalPedido.setText("Total del pedido: $" + String.format("%.2f", total != null ? total : 0.0));
        });

        setupButtons();
        
        orderDetailViewModel.getOrder(orderId).observe(getViewLifecycleOwner(), order -> {
            if(order.getStatus().equals("Activo")){
                bttnAceptarPedido.setOnClickListener(view1 -> changeOrderStatus(orderId,"En preparación"));
            }else if(order.getStatus().equals("En preparación")){
                bttnAceptarPedido.setOnClickListener(view1 -> changeOrderStatus(orderId,"Listo"));
            }
            bttnCancelarPedido.setOnClickListener(view1 -> changeOrderStatus(orderId,"Cancelado"));
        });



        return rootView;
    }

    private void setupButtons() {
        orderDetailViewModel.getOrder(orderId).observe(getViewLifecycleOwner(), order -> {
            if (order != null) {
                bttnAceptarPedido.setVisibility(View.VISIBLE);
                bttnCancelarPedido.setVisibility(View.VISIBLE);

                switch (order.getStatus()) {
                    case "Activo":
                        bttnAceptarPedido.setText("PREPARAR");
                        bttnAceptarPedido.setOnClickListener(view -> changeOrderStatus(orderId, "En preparación"));
                        break;
                    case "En preparación":
                        bttnAceptarPedido.setText("LISTO");
                        bttnAceptarPedido.setOnClickListener(view -> changeOrderStatus(orderId, "Listo"));
                        break;
                    case "Cancelado":
                    case "Listo":
                        bttnAceptarPedido.setVisibility(View.GONE);
                        bttnCancelarPedido.setVisibility(View.GONE);
                        break;
                }

                bttnAceptarPedido.invalidate();
                bttnAceptarPedido.requestLayout();
                bttnCancelarPedido.invalidate();
                bttnCancelarPedido.requestLayout();
            }
        });

        bttnCancelarPedido.setOnClickListener(view -> changeOrderStatus(orderId, "Cancelado"));
    }

    public void changeOrderStatus(long orderId, String newStatus) {
        orderDetailViewModel.updateOrderStatus(orderId, newStatus);

        if (rootView != null) {
            NavController navController = Navigation.findNavController(requireView());
            navController.popBackStack();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        orderDetailViewModel = new ViewModelProvider(this).get(OrderDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}