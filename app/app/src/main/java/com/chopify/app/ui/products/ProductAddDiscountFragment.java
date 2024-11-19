package com.chopify.app.ui.products;

import static android.content.Intent.getIntent;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chopify.app.data.entities.Product;
import com.chopify.app.databinding.FragmentProductAddDiscountBinding;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class ProductAddDiscountFragment extends Fragment {

    public static ProductAddDiscountFragment newInstance() {
        return new ProductAddDiscountFragment();
    }
    private boolean isStartDatePickerVisible = false;
    private boolean isEndDatePickerVisible = false;
    private ProductAddDiscountViewModel ViewModel;
    private FragmentProductAddDiscountBinding biding;
    private Product product;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        biding = FragmentProductAddDiscountBinding.inflate(inflater, container, false);

        ViewModel = new ViewModelProvider(this).get(ProductAddDiscountViewModel.class);

        // Observa y actualiza la fecha de inicio
        ViewModel.getStartDate().observe(getViewLifecycleOwner(), selection -> {
            Date startDate = new Date(selection);
            DateFormat dateFormat = SimpleDateFormat.getDateInstance();
            biding.editTextdateStart.setText(dateFormat.format(startDate));
        });


        ViewModel.getEndDate().observe(getViewLifecycleOwner(), selection -> {
            Date endDate = new Date(selection);
            DateFormat dateFormat = SimpleDateFormat.getDateInstance();
            biding.editTextdateEnd.setText(dateFormat.format(endDate));
        });

        biding.editTextdateStart.setOnClickListener(v -> {
            biding.editTextdateStart.setFocusable(false);
            if (!isStartDatePickerVisible) {
                isStartDatePickerVisible = true;

                MaterialDatePicker<Long> startDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Selecciona una fecha de inicio")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();

                startDatePicker.show(getParentFragmentManager(), "START_DATE_PICKER");

                startDatePicker.addOnPositiveButtonClickListener(selection -> {
                    ViewModel.onStartDateSelected(selection);
                    isStartDatePickerVisible = false;
                });

                startDatePicker.addOnDismissListener(dialog -> {
                    isStartDatePickerVisible = false;
                });
            }
        });

        biding.editTextdateEnd.setOnClickListener(v -> {
            biding.editTextdateEnd.setFocusable(false);
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
                    isEndDatePickerVisible = false;
                });
            }
        });
        Bundle args = getArguments();
        if (args != null) {
             this.product = (Product) args.getSerializable("product");
            Log.d("productID", product.getId() + "");
        }

        biding.buttonSave.setOnClickListener(v -> {
            Log.d("productID", product.getId() + "");
            ViewModel.savePromotion(product.getId(),Objects.requireNonNull(biding.editNamePromo.getText()).toString(),
                Double.parseDouble(Objects.requireNonNull(biding.editDiscount.getText()).toString()) );
            getParentFragmentManager().popBackStack();
        });
        biding.buttonCancel.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        return biding.getRoot();
    }
}