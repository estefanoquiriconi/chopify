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


public class ProductAddDiscountFragment extends Fragment {

    public static ProductAddDiscountFragment newInstance() {
        return new ProductAddDiscountFragment();
    }

    private ProductAddDiscountViewModel mViewModel;
    private FragmentProductAddDiscountBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProductAddDiscountBinding.inflate(inflater, container, false);

       binding. = view.findViewById(R.id.dateEditText);

        // Observa cambios en la fecha seleccionada desde el ViewModel
        viewModel.getSelectedDate().observe(getViewLifecycleOwner(), selection -> {
            // Convierte y muestra la fecha seleccionada en el campo de texto
            Date selectedDate = new Date(selection);
            DateFormat dateFormat = SimpleDateFormat.getDateInstance();
            dateEditText.setText(dateFormat.format(selectedDate));
        });

        dateEditText.setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Selecciona una fecha")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();

            datePicker.show(getParentFragmentManager(), "DATE_PICKER");

            datePicker.addOnPositiveButtonClickListener(selection -> {
                // Envía la fecha seleccionada al ViewModel
                viewModel.onDateSelected(selection);
            });
        });
    }

        return binding.getRoot();
    }



}