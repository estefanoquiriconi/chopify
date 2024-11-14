package com.chopify.app.ui.orders;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.chopify.app.data.entities.Order;
import com.chopify.app.data.entities.OrderDetail;
import com.chopify.app.repositories.OrderDetailRepository;
import com.chopify.app.repositories.OrderRepository;

import java.util.List;

public class OrderDetailViewModel extends AndroidViewModel {
    private final OrderDetailRepository orderDetailRepository;

    public OrderDetailViewModel(@NonNull Application application) {
        super(application);
        long businessId = 2;
        orderDetailRepository = new OrderDetailRepository(application);
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


}