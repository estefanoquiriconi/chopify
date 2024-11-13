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
import com.chopify.app.repositories.CustomerRepository;
import com.chopify.app.repositories.OrderRepository;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private MediatorLiveData<List<Order>> businessOrdersWithCustomers = new MediatorLiveData<>();
    private LiveData<List<Order>> businessOrders;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        orderRepository = new OrderRepository(application);
        customerRepository = new CustomerRepository(application);
    }

    public void init(long businessId) {
        if (businessOrders == null) {
            businessOrders = orderRepository.getBusinessOrders(businessId);

            businessOrdersWithCustomers.addSource(businessOrders, orders -> {
                if (orders != null) {
                    for (Order order : orders) {

                        LiveData<Customer> customerLiveData = customerRepository.getById(order.getCustomerId());
                        businessOrdersWithCustomers.addSource(customerLiveData, customer -> {
                            if (customer != null) {
                                order.setCustomerName(customer.getFirstName());
                                Log.d("OrderViewModelDebug", "Pedido ID: " + order.getId() + ", Cliente: " + customer.getFirstName());
                            }
                            businessOrdersWithCustomers.setValue(orders);
                        });
                    }
                }
            });
        }
    }

    public LiveData<List<Order>> getBusinessOrdersWithCustomers() {
        return businessOrdersWithCustomers;
    }
}
