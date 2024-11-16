package com.chopify.app.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.chopify.app.data.dao.OrderDao;
import com.chopify.app.data.database.AppDataBase;
import com.chopify.app.data.entities.Order;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderRepository {
    private final OrderDao orderDao;
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    public OrderRepository(Application application) {
        AppDataBase appDataBase = AppDataBase.getInstance(application);
        this.orderDao = appDataBase.orderDao();
    }

    public LiveData<Order> getById(long id) {
        return orderDao.getById(id);
    }

    public LiveData<List<Order>> getBusinessOrders(long businessId) {
        return orderDao.getBusinessOrders(businessId);
    }

    public LiveData<List<Order>> getOrdersByStatus(String status) {
        return orderDao.getOrdersByStatus(status);
    }

    public LiveData<List<Order>> getBusinessOrdersByStatus(long businessId, String status) {
        return orderDao.getBusinessOrdersByStatus(businessId, status);
    }

    public void updateOrderStatus(long orderId, String newStatus) {
        executor.execute(() -> {
            orderDao.updateOrderStatus(orderId, newStatus);
        });
    }

    public void insert(Order order) {
        executor.execute(() -> orderDao.insert(order));
    }

    public void update(Order order) {
        executor.execute(() -> orderDao.update(order));
    }
}
