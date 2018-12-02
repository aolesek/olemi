package pl.edu.agh.student.olemi.repositories;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import androidx.room.Room;
import io.reactivex.Flowable;
import pl.edu.agh.student.olemi.daos.IngredientDao;
import pl.edu.agh.student.olemi.daos.ProductDao;
import pl.edu.agh.student.olemi.database.AppDatabase;
import pl.edu.agh.student.olemi.entities.Ingredient;
import pl.edu.agh.student.olemi.entities.Product;
import pl.edu.agh.student.olemi.models.ComplexProductModel;
import pl.edu.agh.student.olemi.models.ProductModel;
import pl.edu.agh.student.olemi.models.SimpleProductModel;

public class PersistedProductRepository {

    private static final String PRODUCT_DB_NAME = "db-olemi-products";

    private final AppDatabase productsDatabase;

    private final ProductDao productDao;

    private final IngredientDao ingredientDao;


    public PersistedProductRepository(Context context) {
        this.productsDatabase = Room.databaseBuilder(context, AppDatabase.class, PRODUCT_DB_NAME)
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();
        this.productDao = productsDatabase.getProductDao();
        this.ingredientDao = productsDatabase.getIngredientDao();
    }

    public void insertProduct(ProductModel product) {
        if (product instanceof SimpleProductModel) {
            insertSimpleProduct((SimpleProductModel) product);
        }
        if (product instanceof ComplexProductModel) {
            insertComplexProduct((ComplexProductModel)product);
        }
    }

    public void insertSimpleProduct(SimpleProductModel simpleProduct) {
        final Product productEntity = new Product(simpleProduct.getName(), simpleProduct.getNutrients(), "simple");
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                productDao.insertProduct(productEntity);
                return null;
            }
        }.execute();
    }

    public void insertComplexProduct(ComplexProductModel complexProduct) {
        final Product productEntity = new Product(complexProduct.getName(), null, "complex");
        productDao.insertProduct(productEntity);
        complexProduct.getIngredients().forEach(ingredientModel -> {
            final Ingredient ingredientEntity = new Ingredient();
            ingredientEntity.setParentProductId(productEntity.getName());
            ingredientEntity.setPartOfTotalWeight(ingredientModel.getPartOfTotalWeight());
            ingredientDao.insertIngredient(ingredientEntity);
        });
    }

    public Flowable<List<Product>> getProducts() {
        return productDao.getProducts();
    }

}
