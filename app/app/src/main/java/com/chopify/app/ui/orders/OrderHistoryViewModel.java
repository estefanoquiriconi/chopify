package com.chopify.app.ui.orders;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.chopify.app.data.entities.Order;
import com.chopify.app.repositories.OrderRepository;

import java.util.List;

public class OrderHistoryViewModel extends AndroidViewModel {
    private final OrderRepository orderRepository;
    private final LiveData<List<Order>> businessHistoryOrders;

    public OrderHistoryViewModel(@NonNull Application application) {
        super(application);
        long businessId = 2;
        orderRepository = new OrderRepository(application);
        businessHistoryOrders = orderRepository.getBusinessOrders(businessId); //modify to get logged bss
    }


    public LiveData<List<Order>> getBusinessHistoryOrders() {
        return businessHistoryOrders;
    }
}