package com.chopify.app.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import com.chopify.app.R;
import com.chopify.app.databinding.FragmentLoginBinding;
import com.chopify.app.helpers.SessionManager;
import com.chopify.app.ui.register.AccountRegistrationActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;


public class LoginFragment extends Fragment {
    private FragmentLoginBinding biding;
    private LoginViewModel loginViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        biding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = biding.getRoot();

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        Fade fadeTransition = new Fade();
        fadeTransition.setDuration(300);
        setEnterTransition(fadeTransition);
        setExitTransition(fadeTransition);

        loginViewModel.getIsEmailValid().observe(getViewLifecycleOwner(), isValid -> {
            biding.emailEditTxt.setError(isValid ? null : getString(R.string.invalid_email));

            if (isValid) {
                biding.editUserName.setEndIconDrawable(R.drawable.check_24);
                biding.editUserName.setEndIconTintList(
                        ContextCompat.getColorStateList(requireContext(), R.color.negro_carbon)
                );
            } else {
                biding.editUserName.setEndIconTintList(
                        ContextCompat.getColorStateList(requireContext(), android.R.color.transparent)
                );
            }
        });

        biding.singUp.setOnClickListener(v -> loginViewModel.onRegisterClicked());
        loginViewModel.getNavigateToRegister().observe(getViewLifecycleOwner(), navigate -> {
            if (navigate) {
                startActivity(new Intent(getActivity(), AccountRegistrationActivity.class));
                loginViewModel.resetNavigationFlags();
            }
        });

        /*Authenticate email*/
        biding.singIn.setOnClickListener(v -> loginViewModel.onSignInClicked(Objects.requireNonNull(biding.emailEditTxt.getText()).toString(), Objects.requireNonNull(biding.passwordEditTxt.getText()).toString()));
        loginViewModel.getNavigateToProducts().observe(getViewLifecycleOwner(), navigate -> {
            if (navigate) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_loginFragment_to_navigation_products, null, new  NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build());
                loginViewModel.resetNavigationFlags();
            }
        });
        loginViewModel.getShowErrorDialog().observe(getViewLifecycleOwner(), show -> {
            if (show) {
                new MaterialAlertDialogBuilder(requireContext(),R.style.CustomAlertDialogTheme)
                        .setTitle("Error al ingresar")
                        .setMessage("Las credenciales ingresadas son incorrectas. Por favor, inténtalo de nuevo.")
                        .setPositiveButton(getString(R.string.aceptar), (dialog, which) -> dialog.dismiss())
                        .show();
                loginViewModel.resetNavigationFlags();
            }
        } );


        loginViewModel.getNavigateToForgotPassword().observe(getViewLifecycleOwner(), navigate -> {
        });
        biding.emailEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginViewModel.validateEmail(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return biding.getRoot();
    }

    private void navigateTo(int actionId, View view) {
        NavController navController = Navigation.findNavController(view);
        navController.navigate(actionId, null, new NavOptions.Builder().build());
    }
}
