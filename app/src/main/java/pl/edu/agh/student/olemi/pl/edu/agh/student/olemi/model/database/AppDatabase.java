package pl.edu.agh.student.olemi.pl.edu.agh.student.olemi.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import pl.edu.agh.student.olemi.pl.edu.agh.student.olemi.model.daos.IngredientDao;
import pl.edu.agh.student.olemi.pl.edu.agh.student.olemi.model.daos.ProductDao;
import pl.edu.agh.student.olemi.pl.edu.agh.student.olemi.model.entities.Ingredient;
import pl.edu.agh.student.olemi.pl.edu.agh.student.olemi.model.entities.Nutrients;
import pl.edu.agh.student.olemi.pl.edu.agh.student.olemi.model.entities.Product;

@Database(entities = {Product.class, Ingredient.class, Nutrients.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ProductDao getProductDao();

    public abstract IngredientDao getIngredientDao();
}