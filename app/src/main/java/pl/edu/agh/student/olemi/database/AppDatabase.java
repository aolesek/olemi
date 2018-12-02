package pl.edu.agh.student.olemi.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import pl.edu.agh.student.olemi.daos.IngredientDao;
import pl.edu.agh.student.olemi.daos.ProductDao;
import pl.edu.agh.student.olemi.entities.Ingredient;
import pl.edu.agh.student.olemi.entities.Nutrients;
import pl.edu.agh.student.olemi.entities.Product;

@Database(entities = {Product.class, Ingredient.class, Nutrients.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ProductDao getProductDao();

    public abstract IngredientDao getIngredientDao();
}