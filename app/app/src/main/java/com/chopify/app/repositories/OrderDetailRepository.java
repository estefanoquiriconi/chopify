package com.chopify.app.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chopify.app.data.dao.OrderDetailDao;
import com.chopify.app.data.database.AppDataBase;
import com.chopify.app.data.entities.OrderDetail;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderDetailRepository {
    private final OrderDetailDao orderDetailDao;
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    public OrderDetailRepository(Application application) {
        AppDataBase appDataBase = AppDataBase.getInstance(application);
        this.orderDetailDao = appDataBase.orderDetailDao();
    }

    public LiveData<List<OrderDetail>> getOrderDetails(long orderId) {
        return orderDetailDao.getOrderDetails(orderId);
    }

    public void insert(OrderDetail detail) {
        executor.execute(() -> orderDetailDao.insert(detail));
    }

    public LiveData<List<Long>> insertAll(List<OrderDetail> details) {
        MutableLiveData<List<Long>> result = new MutableLiveData<>();
        executor.execute(() -> {
            List<Long> ids = orderDetailDao.insertAll(details);
            result.postValue(ids);
        });
        return result;
    }

}
