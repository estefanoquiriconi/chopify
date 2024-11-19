package com.chopify.app.ui.products;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;

import com.chopify.app.data.entities.Promotion;
import com.chopify.app.repositories.ProductDiscountRepository;
import com.chopify.app.repositories.ProductPromotionRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class ProductAddDiscountViewModel extends AndroidViewModel {
    private final ProductDiscountRepository repository = new ProductDiscountRepository(getApplication());
    private final MutableLiveData<Long> startDate = new MutableLiveData<>();
    private final MutableLiveData<Long> endDate = new MutableLiveData<>();
    private final MutableLiveData<Promotion> promotionMutableLiveData = new MutableLiveData<>();
    public ProductAddDiscountViewModel(@NonNull Application application) {
        super(application);
    }


    public void savePromotion(long productId, String name, double discount) {
        if (name.isEmpty() || discount <= 0 || startDate.getValue() == null || endDate.getValue() == null) {
            return;
        }
        Promotion promotion = new Promotion();
        promotion.setName(name);
        promotion.setDiscount(discount);
        promotion.setStartDate(startDate.getValue());
        promotion.setEndDate(endDate.getValue());
        promotion.setActive(true);
        repository.insert(promotion,productId);
        }


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