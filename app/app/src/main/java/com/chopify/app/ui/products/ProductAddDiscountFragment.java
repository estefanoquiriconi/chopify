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

public class ProductAddDiscountFragment extends Fragment {

    public static ProductAddDiscountFragment newInstance() {
        return new ProductAddDiscountFragment();
    }

    private ProductAddDiscountViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_add_discount, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProductAddDiscountViewModel.class);
        // TODO: Use the ViewModel
    }

}