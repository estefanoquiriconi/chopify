package com.chopify.app.ui.login;


import android.app.Application;
import android.content.DialogInterface;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chopify.app.R;
import com.chopify.app.data.entities.Business;
import com.chopify.app.repositories.BusinessRepository;
import com.chopify.app.ui.login.validation.EmailValidation;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class LoginViewModel extends AndroidViewModel {
    private final MutableLiveData<Boolean> isEmailValid = new MutableLiveData<>();
    private final MutableLiveData<Boolean> navigateToRegister = new MutableLiveData<>();
    private final MutableLiveData<Boolean> navigateToForgotPassword = new MutableLiveData<>();
    private final MutableLiveData<Boolean> navigateToProfile = new MutableLiveData<>();
    private final MutableLiveData<Boolean> navigateToProducts = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _showErrorDialog = new MutableLiveData<>();
    private final BusinessRepository businessRepository = new BusinessRepository(getApplication());

    public LoginViewModel(Application application) {
        super(application);
    }


    public LiveData<Boolean> getShowErrorDialog()  {return _showErrorDialog;};
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
            if (business != null  ) {
                if (business.getEmail().equals(email) && business.getPassword().equals(password)) {
                    navigateToProducts.setValue(true);
                }else {
                 _showErrorDialog.setValue(true);
                }

            }else {
                _showErrorDialog.setValue(true);
            }
        });
    }


    public void resetNavigationFlags() {
        navigateToRegister.setValue(false);
        navigateToForgotPassword.setValue(false);
        navigateToProducts.setValue(false);
        navigateToProducts.setValue(false);
        _showErrorDialog.setValue(false);
    }


}
