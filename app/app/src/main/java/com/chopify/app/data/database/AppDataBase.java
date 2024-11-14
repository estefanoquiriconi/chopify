package com.chopify.app.data.database;

import android.content.Context;
import android.util.Log;

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

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
            OrderDao orderDao = dataBase.orderDao();
            OrderDetailDao orderDetailDao = dataBase.orderDetailDao();
            CustomerDao customerDao = dataBase.customerDao();
            DeliveryDao deliveryDao = dataBase.deliveryDao();
            ProductDao productDao = dataBase.productDao();
            CategoryDao categoryDao = dataBase.categoryDao();

            long addressID1 = addressDao.insert(new Address("Av. Libertador", -27.763893, -64.243792));
            long addressID2 = addressDao.insert(new Address("Av. Independencia", -27.791204, -64.269635));
            long addressID3 = addressDao.insert(new Address("Av. Belgrano", -27.789374, -64.260253));
            long businessID1 = businessDao.insert(new Business("cerveza_store@mail.com", "password1", "20-12345678-1",
                    "Cerveza Store", "123456789", addressID1));
            long businessID2 =businessDao.insert(new Business("vinos_finos@mail.com", "password2", "30-87654321-2",
                    "Vinos Finos", "987654321", addressID2));

            long deliveryID1 = deliveryDao.insert(new Delivery("De Yebra", "Marcelo", "marcelo@gmail.com", "33323122", "333231222","La Plata 554"));
            long customerID1 = customerDao.insert(new Customer("Martin", "Koleff", "martincito@gmail.com", "33933333", crearFecha("09:11:2004","12:12"), addressID3));

            long orderID1 = orderDao.insert(new Order(customerID1,businessID2, deliveryID1, crearFecha("09:11:2024","12:12:00"),"Activo"));
            long orderID2 = orderDao.insert(new Order(customerID1,businessID2, deliveryID1, crearFecha("09:11:2024","13:13:00"),"Activo"));
            long orderID3 = orderDao.insert(new Order(customerID1,businessID2, deliveryID1, crearFecha("09:11:2024","06:50:00"),"Activo"));
            long orderID4 =  orderDao.insert(new Order(customerID1,businessID2, deliveryID1, crearFecha("09:11:2024","20:30:00"),"Activo"));
            long orderID5 = orderDao.insert(new Order(customerID1,businessID2, deliveryID1, crearFecha("09:11:2024","21:21:00"),"Activo"));
            long orderID6 =  orderDao.insert(new Order(customerID1,businessID2, deliveryID1, crearFecha("09:11:2024","23:30:00"),"Cancelado"));
            long orderID7 = orderDao.insert(new Order(customerID1,businessID2, deliveryID1, crearFecha("09:11:2024","20:30:00"),"Activo"));
            long orderID8 = orderDao.insert(new Order(customerID1,businessID2, deliveryID1, crearFecha("09:11:2024","21:21:00"),"Cancelado"));
            long orderID9 = orderDao.insert(new Order(customerID1,businessID2, deliveryID1, crearFecha("09:11:2024","23:30:00"),"Cancelado"));

            long categoryID1 = categoryDao.insert(new Category("Cervezas"));

            long productID1 = productDao.insert(new Product("Budweiser","Cerveza",200,20,businessID2,categoryID1));
            long productID2 = productDao.insert(new Product("Imperial","Cerveza",300,30,businessID2,categoryID1));
            long productID3 = productDao.insert(new Product("Brahma","Cerveza",400,40,businessID2,categoryID1));

            long orderDetailID1 = orderDetailDao.insert(new OrderDetail(orderID1,productID1,2,200));
            long orderDetailID2 = orderDetailDao.insert(new OrderDetail(orderID1,productID2,3,300));
            long orderDetailID3 = orderDetailDao.insert(new OrderDetail(orderID1,productID3,4,400));
        });
    }

    private static Date crearFecha(String fecha, String hora) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss", Locale.getDefault());
            sdf.setLenient(false);
            Date resultado = sdf.parse(fecha + " " + hora);
            // Para debug
            SimpleDateFormat formatoDebug = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            Log.d("DateDebug", "Fecha creada: " + formatoDebug.format(resultado));

            return resultado;
        } catch (ParseException e) {
            Log.e("DateError", "Error al parsear fecha: " + e.getMessage());
            return null;
        }
    }

}
