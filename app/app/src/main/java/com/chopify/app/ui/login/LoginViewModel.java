package com.chopify.app.ui.login;


import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.chopify.app.ui.login.validation.EmailValidation;

public class LoginViewModel extends AndroidViewModel {
    private final MutableLiveData<Boolean> isEmailValid = new MutableLiveData<>();
    private final MutableLiveData<Boolean> navigateToRegister = new MutableLiveData<>();
    private final MutableLiveData<Boolean> navigateToForgotPassword = new MutableLiveData<>();
    private final MutableLiveData<Boolean> navigateToProfile = new MutableLiveData<>();

    public LoginViewModel(Application application) {
        super(application);
    }

    public LiveData<Boolean> getIsEmailValid() {
        return isEmailValid;
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

    public void validateEmail(CharSequence email) {
        boolean isValid = EmailValidation.isValidEmail(email.toString());
        isEmailValid.setValue(isValid);
    }

    public void onRegisterClicked() {
        navigateToRegister.setValue(true);
    }

    public void onForgotPasswordClicked() {
        navigateToForgotPassword.setValue(true);
    }

    public void onSignInClicked() {
        navigateToProfile.setValue(true);
    }


    public void resetNavigationFlags() {
        navigateToRegister.setValue(false);
        navigateToForgotPassword.setValue(false);
        navigateToProfile.setValue(false);
    }
}
