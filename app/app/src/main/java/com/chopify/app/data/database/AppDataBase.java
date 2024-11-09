package com.chopify.app.data.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.chopify.app.data.dao.AddressDao;
import com.chopify.app.data.dao.BusinessDao;
import com.chopify.app.data.dao.BusinessScheduleDao;
import com.chopify.app.data.dao.CategoryDao;
import com.chopify.app.data.dao.CustomerDao;
import com.chopify.app.data.dao.DeliveryDao;
import com.chopify.app.data.dao.OrderDao;
import com.chopify.app.data.dao.OrderDetailDao;
import com.chopify.app.data.dao.OrderTrackingDao;
import com.chopify.app.data.dao.ProductDao;
import com.chopify.app.data.dao.ProductPromotionDao;
import com.chopify.app.data.dao.PromotionDao;
import com.chopify.app.data.entities.Address;
import com.chopify.app.data.entities.Business;
import com.chopify.app.data.entities.BusinessSchedule;
import com.chopify.app.data.entities.Category;
import com.chopify.app.data.entities.Customer;
import com.chopify.app.data.entities.Delivery;
import com.chopify.app.data.entities.Order;
import com.chopify.app.data.entities.OrderDetail;
import com.chopify.app.data.entities.OrderTracking;
import com.chopify.app.data.entities.Product;
import com.chopify.app.data.entities.ProductPromotion;
import com.chopify.app.data.entities.Promotion;
import com.chopify.app.utils.DateConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {
                Address.class,
                Business.class,
                Customer.class,
                Category.class,
                Product.class,
                Delivery.class,
                Order.class,
                OrderTracking.class,
                OrderDetail.class,
                BusinessSchedule.class,
                Promotion.class,
                ProductPromotion.class
        },
        version = 1
)
@TypeConverters({DateConverter.class})
public abstract class AppDataBase extends RoomDatabase {
    private static final String DATABASE_NAME = "chopify_db";
    private static volatile AppDataBase INSTANCE;

    public abstract AddressDao addressDao();

    public abstract BusinessDao businessDao();

    public abstract CustomerDao customerDao();

    public abstract CategoryDao categoryDao();

    public abstract ProductDao productDao();

    public abstract DeliveryDao deliveryDao();

    public abstract OrderDao orderDao();

    public abstract OrderTrackingDao orderTrackingDao();

    public abstract OrderDetailDao
    orderDetailDao();

    public abstract BusinessScheduleDao businessScheduleDao();

    public abstract PromotionDao promotionDao();

    public abstract ProductPromotionDao productPromotionDao();

    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    public static synchronized AppDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            populateDatabase(INSTANCE);
        }
    };

    private static void populateDatabase(AppDataBase dataBase) {
        executor.execute(() -> {
            AddressDao addressDao = dataBase.addressDao();
            BusinessDao businessDao = dataBase.businessDao();

            long addressID1 = addressDao.insert(new Address("Av. Libertador", -27.763893, -64.243792));
            long addressID2 = addressDao.insert(new Address("Av. Independencia", -27.791204, -64.269635));

            businessDao.insert(new Business("cerveza_store@mail.com", "password1", "20-12345678-1",
                    "Cerveza Store", "123456789", addressID1));
            businessDao.insert(new Business("vinos_finos@mail.com", "password2", "30-87654321-2",
                    "Vinos Finos", "987654321", addressID2));
        });
    }

}
