package com.chopify.app.ui.business;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chopify.app.data.relationships.BusinessWithAddress;
import com.chopify.app.helpers.SessionManager;
import com.chopify.app.repositories.BusinessRepository;

public class BusinessViewModel extends AndroidViewModel {

    private final BusinessRepository businessRepository;
    private final LiveData<BusinessWithAddress> business;
    private final MutableLiveData<Boolean> logOut = new MutableLiveData<>();
    private final   SessionManager sessionManager;
    public LiveData<Boolean> getLogOut()  {return logOut;};

    public BusinessViewModel(@NonNull Application application) {
        super(application);
        businessRepository = new BusinessRepository(application);
       sessionManager = new SessionManager(getApplication().getApplicationContext());
        business = businessRepository.findByEmailWithAddress(sessionManager.getBusiness().getEmail());
    }
public void onLogutListener(){
        sessionManager.deleteBusiness();
        logOut.setValue(true);
}
    public LiveData<BusinessWithAddress> getBusiness() {
        return business;
    }

}