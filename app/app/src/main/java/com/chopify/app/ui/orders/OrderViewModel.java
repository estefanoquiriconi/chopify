package com.chopify.app.ui.orders;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.chopify.app.data.entities.Customer;
import com.chopify.app.data.entities.Order;
import com.chopify.app.data.entities.OrderDetail;
import com.chopify.app.repositories.CustomerRepository;
import com.chopify.app.repositories.OrderDetailRepository;
import com.chopify.app.repositories.OrderRepository;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private OrderDetailRepository orderDetailRepository;

    private MediatorLiveData<List<Order>> businessOrdersWithCustomers = new MediatorLiveData<>();
    private LiveData<List<Order>> businessOrders;
    private LiveData<List<OrderDetail>> orderDetails;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        orderRepository = new OrderRepository(application);
        customerRepository = new CustomerRepository(application);
        orderDetailRepository = new OrderDetailRepository(application);
    }

    public void init(long businessId) {
        if (businessOrders == null) {
            businessOrders = orderRepository.getBusinessOrders(businessId);
            orderDetails = orderDetailRepository.getOrderDetails(businessId);

            businessOrdersWithCustomers.addSource(businessOrders, orders -> {
                if (orders != null && orderDetails.getValue() != null) {
                    updateOrdersWithCustomerData(orders, orderDetails.getValue());
                }
            });

            businessOrdersWithCustomers.addSource(orderDetails, details -> {
                if (businessOrders.getValue() != null && details != null) {
                    updateOrdersWithCustomerData(businessOrders.getValue(), details);
                }
            });
        }
    }

    private void updateOrdersWithCustomerData(List<Order> orders, List<OrderDetail> details) {
        for (Order order : orders) {
            // Filtrar y contar los productos de este pedido
            int cantidadProductos = 0;
            for (OrderDetail orderDetail : details) {
                if (orderDetail.getOrderId() == order.getId()) {
                    cantidadProductos++;
                }
            }
            order.setCantidadProductos(cantidadProductos);

            // Obtener los datos del cliente
            LiveData<Customer> customerLiveData = customerRepository.getById(order.getCustomerId());
            businessOrdersWithCustomers.addSource(customerLiveData, customer -> {
                if (customer != null) {
                    order.setCustomerName(customer.getFirstName());
                    // Después de actualizar el cliente, actualizamos la lista completa
                    businessOrdersWithCustomers.setValue(orders);
                }
            });
        }
    }

    public LiveData<List<Order>> getBusinessOrdersWithCustomers() {
        return businessOrdersWithCustomers;
    }
}
