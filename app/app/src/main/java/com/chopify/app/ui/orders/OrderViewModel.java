package com.chopify.app.ui.orders;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.chopify.app.data.entities.Order;
import com.chopify.app.data.entities.OrderDetail;
import com.chopify.app.repositories.CustomerRepository;
import com.chopify.app.repositories.OrderDetailRepository;
import com.chopify.app.repositories.OrderRepository;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderDetailRepository orderDetailRepository;

    private final MediatorLiveData<List<Order>> businessOrdersWithDetails = new MediatorLiveData<>();
    private LiveData<List<Order>> businessOrders;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        orderRepository = new OrderRepository(application);
        customerRepository = new CustomerRepository(application);
        orderDetailRepository = new OrderDetailRepository(application);
    }

    public void init(long businessId) {

        if (businessOrders == null) {
            businessOrders = orderRepository.getBusinessOrders(businessId);

            businessOrdersWithDetails.addSource(businessOrders, orders -> {
                if (orders != null) {
                    updateOrdersWithCustomerAndDetails(orders);
                }
            });
        }
    }

    private void updateOrdersWithCustomerAndDetails(List<Order> orders) {
        for (Order order : orders) {
            LiveData<List<OrderDetail>> orderDetailsLiveData = orderDetailRepository.getOrderDetails(order.getId());
            businessOrdersWithDetails.addSource(orderDetailsLiveData, details -> {
                if (details != null) {
                    order.setCantidadProductos(details.size());
                    customerRepository.getByIdSync(order.getCustomerId(), customer -> {
                        if (customer != null) {
                            order.setCustomerName(customer.getFirstName());
                        } else {
                            order.setCustomerName("desconocido");
                        }
                        businessOrdersWithDetails.postValue(orders);
                    });

                }
            });
        }
    }


    public LiveData<List<Order>> getBusinessOrdersWithDetails() {
        return businessOrdersWithDetails;
    }

    public void refreshOrders() {
        if (businessOrders != null && businessOrders.getValue() != null) {
            updateOrdersWithCustomerAndDetails(businessOrders.getValue());
        }
    }

}
