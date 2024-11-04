package com.chopify.app.ui.login.validation;

import android.content.Context;
import android.widget.EditText;
import androidx.core.content.ContextCompat;
import com.chopify.app.R;
import com.google.android.material.textfield.TextInputLayout;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation {

    // Expresión regular para validar el formato del correo electrónico
    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    // Método para validar el formato del correo electrónico
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher= pattern.matcher(email);

        return matcher.matches();
    }


}
