package com.chopify.app.ui.orders;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.chopify.app.data.entities.Order;
import com.chopify.app.repositories.OrderRepository;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {
    private final OrderRepository orderRepository;
    private final LiveData<List<Order>> businessOrders;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        long businessId = 2;
        orderRepository = new OrderRepository(application);
        businessOrders = orderRepository.getBusinessOrders(businessId); //modify to get logged bss
    }


    public LiveData<List<Order>> getBusinessOrders() {
        return businessOrders;
    }

}