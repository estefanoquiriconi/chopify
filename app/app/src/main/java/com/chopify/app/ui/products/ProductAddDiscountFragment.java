package com.chopify.app.ui.products;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chopify.app.R;
import com.chopify.app.databinding.FragmentProductAddDiscountBinding;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ProductAddDiscountFragment extends Fragment {

    public static ProductAddDiscountFragment newInstance() {
        return new ProductAddDiscountFragment();
    }
    private boolean isStartDatePickerVisible = false;
    private boolean isEndDatePickerVisible = false;
    private ProductAddDiscountViewModel ViewModel;
    private FragmentProductAddDiscountBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProductAddDiscountBinding.inflate(inflater, container, false);

        ViewModel = new ViewModelProvider(this).get(ProductAddDiscountViewModel.class);

        // Observa y actualiza la fecha de inicio
        ViewModel.getStartDate().observe(getViewLifecycleOwner(), selection -> {
            Date startDate = new Date(selection);
            DateFormat dateFormat = SimpleDateFormat.getDateInstance();
            binding.editTextdateStart.setText(dateFormat.format(startDate));
        });

        // Observa y actualiza la fecha de fin
        ViewModel.getEndDate().observe(getViewLifecycleOwner(), selection -> {
            Date endDate = new Date(selection);
            DateFormat dateFormat = SimpleDateFormat.getDateInstance();
            binding.editTextdateEnd.setText(dateFormat.format(endDate));
        });

        binding.editTextdateStart.setOnClickListener(v -> {
            binding.editTextdateStart.setFocusable(false);
            if (!isStartDatePickerVisible) {
                isStartDatePickerVisible = true;

                MaterialDatePicker<Long> startDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Selecciona una fecha de inicio")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();

                startDatePicker.show(getParentFragmentManager(), "START_DATE_PICKER");

                startDatePicker.addOnPositiveButtonClickListener(selection -> {
                    ViewModel.onStartDateSelected(selection);
                    isStartDatePickerVisible = false; // Fecha seleccionada, se puede abrir nuevamente
                });

                startDatePicker.addOnDismissListener(dialog -> {
                    isStartDatePickerVisible = false; // Si el picker se cierra sin seleccionar, permitir el siguiente clic
                });
            }
        });

        binding.editTextdateEnd.setOnClickListener(v -> {
            binding.editTextdateEnd.setFocusable(false);
            if (!isEndDatePickerVisible) {
                isEndDatePickerVisible = true;

                MaterialDatePicker<Long> endDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Selecciona una fecha de fin")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();

                endDatePicker.show(getParentFragmentManager(), "END_DATE_PICKER");

                endDatePicker.addOnPositiveButtonClickListener(selection -> {
                    ViewModel.onEndDateSelected(selection);
                    isEndDatePickerVisible = false;
                });

                endDatePicker.addOnDismissListener(dialog -> {
                    isEndDatePickerVisible = false; // Permite abrir el picker de nuevo
                });
            }
        });
        return binding.getRoot();
    }
}