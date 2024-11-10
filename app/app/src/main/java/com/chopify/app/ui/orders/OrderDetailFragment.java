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
import com.chopify.app.data.entities.Product;
import com.chopify.app.ui.products.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailFragment extends Fragment {

    private OrderDetailViewModel mViewModel;
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

        //recycler view
        recyclerView = view.findViewById(R.id.rvProductosDelPedido);

        obtenerListaProductosDelPedido();

        adaptador = new ProductAdapter(getContext(), listaProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptador);

        //nav to orderDetail
        

        return view;
    }

    private void obtenerListaProductosDelPedido() {
        Product producto = new Product();

        producto.setId(1);
        producto.setName("Nombre 1");
        producto.setDescription("Descripcion 1");
        producto.setPrice(100);
        listaProductos.add(producto);

        producto = new Product();
        producto.setId(2);
        producto.setName("Nombre 2");
        producto.setDescription("Descripcion 2");
        producto.setPrice(200);
        listaProductos.add(producto);

        producto = new Product();
        producto.setId(3);
        producto.setName("Nombre 3");
        producto.setDescription("Descripcion 3");
        producto.setPrice(300);
        listaProductos.add(producto);
    }

    @Override
    public void onResume() {
        super.onResume();
        listaProductos.clear();
        this.obtenerListaProductosDelPedido();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(OrderDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}