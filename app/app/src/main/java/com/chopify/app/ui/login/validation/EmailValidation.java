package com.chopify.app.ui.login.validation;

import android.util.Patterns;

public class EmailValidation {
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
