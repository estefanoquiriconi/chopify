package com.chopify.app.ui.login;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chopify.app.data.entities.Business;
import com.chopify.app.repositories.BusinessRepository;
import com.chopify.app.ui.login.validation.EmailValidation;

public class LoginViewModel extends AndroidViewModel {
    private final MutableLiveData<Boolean> isEmailValid = new MutableLiveData<>();
    private final MutableLiveData<Boolean> navigateToRegister = new MutableLiveData<>();
    private final MutableLiveData<Boolean> navigateToForgotPassword = new MutableLiveData<>();
    private final MutableLiveData<Boolean> navigateToProfile = new MutableLiveData<>();
    private final MutableLiveData<Boolean> navigateToProducts = new MutableLiveData<>();

    private final BusinessRepository businessRepository = new BusinessRepository(getApplication());

    public LoginViewModel(Application application) {
        super(application);
    }



    public LiveData<Boolean> getNavigateToRegister() {
        return navigateToRegister;
    }
    public LiveData<Boolean> getNavigateToForgotPassword() {
        return navigateToForgotPassword;
    }
    public LiveData<Boolean> getNavigateToProfile() {
        return navigateToProfile;
    }
    public LiveData<Boolean> getIsEmailValid() {return isEmailValid;}
    public LiveData<Boolean> getNavigateToProducts() {return navigateToProducts; }
    public void validateEmail(@NonNull CharSequence email) {
        isEmailValid.setValue(EmailValidation.isValidEmail(email.toString()));
    }

    public void onRegisterClicked() {
        navigateToRegister.setValue(true);
    }

    public void onForgotPasswordClicked() {
        navigateToForgotPassword.setValue(true);
    }

    public void onSignInClicked(String email, String password) {
        authenticateUser(email,password);
    }

    public void authenticateUser(String email, String password) {

        businessRepository.findByEmail(email).observeForever(business -> {
            if (business != null) {
                if (business.getEmail().equals(email) && business.getPassword().equals(password)) {
                    navigateToProducts.setValue(true);
                    Log.e("autenticate", "logincorrecto");
                }
                Log.e("autenticate", "login error");
            }
        });
    }


    public void resetNavigationFlags() {
        navigateToRegister.setValue(false);
        navigateToForgotPassword.setValue(false);
        navigateToProducts.setValue(false);
    }


}
