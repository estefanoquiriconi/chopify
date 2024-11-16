package com.chopify.app.ui.orders;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.chopify.app.data.entities.Order;
import com.chopify.app.data.entities.OrderDetail;
import com.chopify.app.data.entities.Product;
import com.chopify.app.repositories.OrderDetailRepository;
import com.chopify.app.repositories.OrderRepository;
import com.chopify.app.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetailViewModel extends AndroidViewModel {
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final MediatorLiveData<Double> totalPedido = new MediatorLiveData<>();
    private final OrderRepository orderRepository;

    public OrderDetailViewModel(@NonNull Application application) {
        super(application);
        orderDetailRepository = new OrderDetailRepository(application);
        productRepository = new ProductRepository(application);
        orderRepository = new OrderRepository(application);
    }

    public LiveData<List<OrderDetail>> getOrderDetails(long orderId) {
        return orderDetailRepository.getOrderDetails(orderId);
    }

    public void insert(OrderDetail detail) {
        orderDetailRepository.insert(detail);
    }

    public LiveData<List<Long>> insertAll(List<OrderDetail> details) {
        return orderDetailRepository.insertAll(details);
    }

    public LiveData<List<Product>> getProductsByOrderId(long orderId) {
        MediatorLiveData<List<Product>> result = new MediatorLiveData<>();
        LiveData<List<OrderDetail>> orderDetailList = orderDetailRepository.getOrderDetails(orderId);

        result.addSource(orderDetailList, details -> {
            if (details != null && !details.isEmpty()) {
                List<Long> productIds = new ArrayList<>();
                Map<Long, Integer> productQuantityMap = new HashMap<>();

                for (OrderDetail detail : details) {
                    productIds.add(detail.getProductId());
                    productQuantityMap.put(detail.getProductId(), detail.getQuantity());
                }

                LiveData<List<Product>> products = productRepository.getProductsByIds(productIds);
                result.addSource(products, productList -> {
                    if (productList != null) {
                        for (Product product : productList) {
                            Integer quantity = productQuantityMap.get(product.getId());
                            if (quantity != null) {
                                product.setQuantity(quantity);
                            }
                        }
                    }
                    result.setValue(productList);
                });
            } else {
                result.setValue(Collections.emptyList());
            }
        });

        return result;
    }

    public LiveData<Double> getTotalPedido(long orderId) {
        LiveData<List<OrderDetail>> orderDetailsLiveData = orderDetailRepository.getOrderDetails(orderId);

        totalPedido.addSource(orderDetailsLiveData, orderDetails -> {
            if (orderDetails != null && !orderDetails.isEmpty()) {
                List<Long> productIds = new ArrayList<>();
                Map<Long, Integer> productQuantityMap = new HashMap<>();

                for (OrderDetail detail : orderDetails) {
                    productIds.add(detail.getProductId());
                    productQuantityMap.put(detail.getProductId(), detail.getQuantity());
                }

                LiveData<List<Product>> productsLiveData = productRepository.getProductsByIds(productIds);
                totalPedido.addSource(productsLiveData, productList -> {
                    if (productList != null) {
                        double total = 0.0;
                        // Calculamos el total
                        for (Product product : productList) {
                            Integer quantity = productQuantityMap.get(product.getId());
                            if (quantity != null) {
                                total += product.getPrice() * quantity;
                            }
                        }
                        totalPedido.setValue(total);
                    }
                });
            } else {
                totalPedido.setValue(0.0);
            }
        });

        return totalPedido;
    }

    public LiveData<Order> getOrder(long orderId) {
        return orderRepository.getById(orderId);
    }

    public void updateOrderStatus(long orderId, String newStatus) {
        orderRepository.updateOrderStatus(orderId, newStatus);
    }
}