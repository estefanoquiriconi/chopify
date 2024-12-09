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
            PromotionDao promotionDao = dataBase.promotionDao();
            ProductPromotionDao productPromotionDao = dataBase.productPromotionDao();

            //Categorías
            long categoryBeerId = categoryDao.insert(new Category("Cerveza"));
            long categoryWineId = categoryDao.insert(new Category("Vino"));
            long categoryVodkaId = categoryDao.insert(new Category("Vodka"));
            long categoryFernetId = categoryDao.insert(new Category("Fernet"));
          
            //Direcciones
            long addressID1 = addressDao.insert(new Address("Av. Libertador", -27.763893, -64.243792));
            long addressID2 = addressDao.insert(new Address("Av. Independencia", -27.791204, -64.269635));
            long addressID3 = addressDao.insert(new Address("Av. Belgrano", -27.789374, -64.260253));
          
            //Negocios
            long businessID1 = businessDao.insert(new Business("cerveza_store@mail.com", "password1", "20-12345678-1",
                    "Cerveza Store", "123456789", addressID1));
            long businessID2 =businessDao.insert(new Business("vinos_finos@mail.com", "password2", "30-87654321-2",
                    "Vinos Finos", "987654321", addressID2));
          
            //Deliverty
            long deliveryID1 = deliveryDao.insert(new Delivery("De Yebra", "Marcelo", "marcelo@gmail.com", "33323122", "333231222","La Plata 554"));
            //Cliente
            long customerID1 = customerDao.insert(new Customer("Martin", "Koleff", "martincito@gmail.com", "33933333", crearFecha("09:11:2004","12:12"), addressID3));
        
            //Productos
            productDao.insert(new Product("Quilmes", "El sabor del encuentro", 2000, 10, businessID1, categoryBeerId));
            productDao.insert(new Product("Branca", "El sabor del Branca", 80000, 5, businessID1, categoryFernetId));
            productDao.insert(new Product("Corona", "Cerveza Corona", 2200, 15, businessID1, categoryBeerId));
            productDao.insert(new Product("Amstel", "Cerveza Amstel", 2300, 8, businessID1, categoryBeerId));
            productDao.insert(new Product("Cerveza Patagonia", "Cerveza Patagonia", 2500, 12, businessID1, categoryBeerId));

            productDao.insert(new Product("Malbec", "Vino Malbec de Mendoza", 3500, 20, businessID1, categoryWineId));
            productDao.insert(new Product("Cabernet Sauvignon", "Vino Cabernet Sauvignon", 4000, 8, businessID1, categoryWineId));
            productDao.insert(new Product("Chardonnay", "Vino Chardonnay", 3000, 18, businessID1, categoryWineId));

            productDao.insert(new Product("Absolut", "Vodka Absolut", 5000, 25, businessID1, categoryVodkaId));
            productDao.insert(new Product("Smirnoff", "Vodka Smirnoff", 4500, 30, businessID1, categoryVodkaId));
          
            long productID1 = productDao.insert(new Product("Budweiser","Cerveza",200,20,businessID2,categoryBeerId));
            long productID2 = productDao.insert(new Product("Imperial","Cerveza",300,30,businessID2,categoryBeerId));
            long productID3 = productDao.insert(new Product("Brahma","Cerveza",400,40,businessID2,categoryBeerId));

            //Pedidos
            long orderID1 = orderDao.insert(new Order(customerID1,businessID2, deliveryID1, crearFecha("09:11:2024","12:12:00"),"Activo"));
            long orderID2 = orderDao.insert(new Order(customerID1,businessID2, deliveryID1, crearFecha("09:11:2024","13:13:00"),"Activo"));
            long orderID3 =  orderDao.insert(new Order(customerID1,businessID2, deliveryID1, crearFecha("09:11:2024","23:30:00"),"Cancelado"));
            long orderID4 = orderDao.insert(new Order(customerID1,businessID2, deliveryID1, crearFecha("09:11:2024","20:30:00"),"Activo"));
            long orderID5 = orderDao.insert(new Order(customerID1,businessID2, deliveryID1, crearFecha("09:11:2024","21:21:00"),"Cancelado"));
            long orderID6 = orderDao.insert(new Order(customerID1,businessID2, deliveryID1, crearFecha("09:11:2024","23:30:00"),"Cancelado"));
        
            //Detalles
            long orderDetailID1 = orderDetailDao.insert(new OrderDetail(orderID1,productID1,2,200));
            long orderDetailID2 = orderDetailDao.insert(new OrderDetail(orderID1,productID2,3,300));
            long orderDetailID3 = orderDetailDao.insert(new OrderDetail(orderID4,productID3,4,400));
            long orderDetailID4 = orderDetailDao.insert(new OrderDetail(orderID2,productID1,2,200));
            long orderDetailID5 = orderDetailDao.insert(new OrderDetail(orderID2,productID2,3,300));
            long orderDetailID6 = orderDetailDao.insert(new OrderDetail(orderID2,productID3,4,400));
            long orderDetailID7 = orderDetailDao.insert(new OrderDetail(orderID3,productID1,2,200));
            long orderDetailID8 = orderDetailDao.insert(new OrderDetail(orderID5,productID2,3,300));
            long orderDetailID9 = orderDetailDao.insert(new OrderDetail(orderID6,productID2,3,300));

            //Promociones
            long promoID1 = promotionDao.insert(new Promotion("Promo 1","20 de descuento", 20,crearFecha("01:11:2024","00:00").getTime(), crearFecha("01:12:2024","00:00").getTime(), true));

            //Promociones y productos
            long productPromoID1 = productPromotionDao.insert(new ProductPromotion(productID1, promoID1));
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
