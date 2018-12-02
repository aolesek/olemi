package pl.edu.agh.student.olemi.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import pl.edu.agh.student.olemi.entities.Ingredient;

@Dao
public interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIngredients(Ingredient... ingredients);

    @Insert
    void insertIngredient(Ingredient ingredient);
}
