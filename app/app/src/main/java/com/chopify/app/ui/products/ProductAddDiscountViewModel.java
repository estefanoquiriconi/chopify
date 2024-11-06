package com.chopify.app.ui.products;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProductAddDiscountViewModel extends ViewModel {

    private final MutableLiveData<Long> startDate = new MutableLiveData<>();
    private final MutableLiveData<Long> endDate = new MutableLiveData<>();

    public LiveData<Long> getStartDate() {
        return startDate;
    }

    public LiveData<Long> getEndDate() {
        return endDate;
    }

    public void onStartDateSelected(long date) {
        startDate.setValue(date);
    }

    public void onEndDateSelected(long date) {
        endDate.setValue(date);
    }
}