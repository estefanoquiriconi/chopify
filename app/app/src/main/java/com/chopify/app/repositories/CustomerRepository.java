package com.chopify.app.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.chopify.app.data.dao.CustomerDao;
import com.chopify.app.data.dao.OrderDao;
import com.chopify.app.data.database.AppDataBase;
import com.chopify.app.data.entities.Customer;
import com.chopify.app.data.entities.Order;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomerRepository {
    private final CustomerDao customerDao;
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    public CustomerRepository(Application application) {
        AppDataBase appDataBase = AppDataBase.getInstance(application);
        this.customerDao = appDataBase.customerDao();
    }

    public LiveData<Customer> getById(long id) {
        return customerDao.getById(id);
    }

    public void getByIdSync(long id, CustomerCallback callback) {
        executor.execute(() -> {
            Customer customer = customerDao.getByIdSync(id);
            if (callback != null) {
                callback.onResult(customer);
            }
        });
    }

    public interface CustomerCallback {
        void onResult(Customer customer);
    }

    public void insert(Customer customer) {
        executor.execute(() -> customerDao.insert(customer));
    }
    public void delete(Customer customer) {
        executor.execute(() -> customerDao.delete(customer));
    }
    public void update(Customer customer) {executor.execute(() -> customerDao.update(customer));
    }
}
