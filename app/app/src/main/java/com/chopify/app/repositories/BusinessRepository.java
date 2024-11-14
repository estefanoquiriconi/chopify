package com.chopify.app.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.chopify.app.data.dao.BusinessDao;
import com.chopify.app.data.database.AppDataBase;
import com.chopify.app.data.entities.Business;
import com.chopify.app.data.relationships.BusinessWithAddress;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BusinessRepository {
    private final BusinessDao businessDao;
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    public BusinessRepository(Application application) {
        AppDataBase appDataBase = AppDataBase.getInstance(application);
        this.businessDao = appDataBase.businessDao();
    }

    public LiveData<Business> getById(long id) {
        return businessDao.getById(id);
    }

    public LiveData<BusinessWithAddress> getByIdWithAddress(long id) {
        return businessDao.getByIdWithAddress(id);
    }

    public LiveData<Business> findByEmail(String email) {
        return businessDao.findByEmail(email);
    }

    public LiveData<BusinessWithAddress> findByEmailWithAddress(String email) {
        return businessDao.findByEmailWithAddress(email);
    }


    public void insert(Business business) {
        executor.execute(() -> businessDao.insert(business));
    }

    public void update(Business business) {
        executor.execute(() -> businessDao.update(business));
    }

    public void delete(Business business) {
        executor.execute(() -> businessDao.delete(business));
    }

}
