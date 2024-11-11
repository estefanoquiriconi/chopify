package com.chopify.app.ui.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chopify.app.R;
import com.chopify.app.data.entities.Order;
import com.chopify.app.data.entities.Product;
import com.chopify.app.databinding.FragmentProductsBinding;
import com.chopify.app.ui.orders.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment {

    private FragmentProductsBinding binding;

    private RecyclerView recyclerView;
    private ProductAdapter adaptador;
    private List<Product> listaProductos = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProductsViewModel productsViewModel =
                new ViewModelProvider(this).get(ProductsViewModel.class);

        binding = FragmentProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.rvProductos);

        obtenerListaProductos();

        adaptador = new ProductAdapter(getContext(), listaProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptador);
        return root;
    }

    private void obtenerListaProductos() {
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
        this.obtenerListaProductos();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}