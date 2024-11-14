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
import com.chopify.app.data.dao.OrderDetailDao;
import com.chopify.app.data.entities.Order;
import com.chopify.app.data.entities.Product;
import com.chopify.app.ui.products.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailFragment extends Fragment {

    private OrderDetailViewModel orderDetailViewModel;
    private RecyclerView recyclerView;
    private ProductAdapter adaptador;
    private List<Product> listaProductos = new ArrayList<>();

    public static OrderDetailFragment newInstance() {
        return new OrderDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);

        orderDetailViewModel = new ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(OrderDetailViewModel.class);

        if (getArguments() != null) {
            Long orderId = getArguments().getLong("orderId");
            Log.d("OrderDetailDebug", "onCreateView: el id del pedido es " + orderId);
        }

        //recycler view
        recyclerView = view.findViewById(R.id.rvProductosDelPedido);


        adaptador = new ProductAdapter(getContext(), listaProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptador);

        return view;
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