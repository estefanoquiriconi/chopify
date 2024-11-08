package com.chopify.app.ui.business;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chopify.app.data.relationships.BusinessWithAddress;
import com.chopify.app.repositories.BusinessRepository;

public class BusinessViewModel extends AndroidViewModel {

    private final BusinessRepository businessRepository;
    private final LiveData<BusinessWithAddress> business;

    public BusinessViewModel(@NonNull Application application) {
        super(application);
        businessRepository = new BusinessRepository(application);
        business = businessRepository.findByEmailWithAddress("vinos_finos@mail.com");
    }

    public LiveData<BusinessWithAddress> getBusiness() {
        return business;
    }

}