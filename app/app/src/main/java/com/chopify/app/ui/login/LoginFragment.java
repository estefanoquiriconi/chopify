package com.chopify.app.ui.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.chopify.app.R;
import com.chopify.app.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        // Configurar la Toolbar si estás usando una personalizada


        Fade fadeTransition = new Fade();
        fadeTransition.setDuration(300);
        setEnterTransition(fadeTransition);
        setExitTransition(fadeTransition);

        // Observa los cambios en la validación de email
        loginViewModel.getIsEmailValid().observe(getViewLifecycleOwner(), isValid -> {
            binding.emailEditTxt.setError(isValid ? null : getString(R.string.invalid_email));


            if (isValid) {
                binding.editUserName.setEndIconDrawable(R.drawable.check_24);
                binding.editUserName.setEndIconTintList(

                        ContextCompat.getColorStateList(requireContext(), R.color.negro_carbon)
                );
            } else {

                binding.editUserName.setEndIconTintList(
                        ContextCompat.getColorStateList(requireContext(), android.R.color.transparent)
                );
            }
        });


        binding.singUp.setOnClickListener(v -> { });

        // Observa las acciones de navegación
        loginViewModel.getNavigateToRegister().observe(getViewLifecycleOwner(), navigate -> {
        /*    if (navigate) {
                navigateTo(R.id.action_login_to_register, view);
                loginViewModel.resetNavigationFlags();
            }*/
        });

        loginViewModel.getNavigateToForgotPassword().observe(getViewLifecycleOwner(), navigate -> {
          /*     if (navigate) {
                navigateTo(R.id.action_login_to_forgot_password, view);
                loginViewModel.resetNavigationFlags();
            }*/
        });

        loginViewModel.getNavigateToProfile().observe(getViewLifecycleOwner(), navigate -> {
           /* if (navigate) {
                navigateTo(R.id.action_login_to_profile, view);
                loginViewModel.resetNavigationFlags();
            }*/
        });

        // Configura el TextWatcher en el campo de email
        binding.emailEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginViewModel.validateEmail(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Configura los listeners para los botones
       /* binding.singUp.setOnClickListener(v -> loginViewModel.onRegisterClicked());
        binding.forgotPassword.setOnClickListener(v -> loginViewModel.onForgotPasswordClicked());
        binding.singIn.setOnClickListener(v -> loginViewModel.onSignInClicked());
*/
        return view;
    }

    private void navigateTo(int actionId, View view) {
        NavController navController = Navigation.findNavController(view);
        navController.navigate(actionId, null, new NavOptions.Builder().build());
    }
}
