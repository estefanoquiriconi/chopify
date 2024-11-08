package com.chopify.app.ui.login.validation;

import android.util.Patterns;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation {

    // Expresión regular para validar el formato del correo electrónico
 /*   private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";*/



    // Método para validar el formato del correo electrónico
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Método para validar el email
   /* public static String validateEmail(String email) {

        if (email == null || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Email inválido";
        } else {
           return null;
        }
    }*/
}
