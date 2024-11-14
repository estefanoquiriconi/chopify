package com.chopify.app.repositories;

import android.app.Application;

import com.chopify.app.data.dao.AddressDao;
import com.chopify.app.data.database.AppDataBase;
import com.chopify.app.data.entities.Address;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AddressRepository {

    private final AddressDao addressDao;
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    public AddressRepository(Application application) {
        AppDataBase appDataBase = AppDataBase.getInstance(application);
        this.addressDao = appDataBase.addressDao();
    }

    public long insert(Address address) {
        Future<Long> future = executor.submit(() -> addressDao.insert(address));
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return 1;
        }
    }


    public void update(Address address) {
        executor.execute(() -> addressDao.update(address));
    }

    public void delete(Address address) {
        executor.execute(() -> addressDao.delete(address));
    }

}
