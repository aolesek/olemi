package pl.edu.agh.student.olemi.pl.edu.agh.student.olemi.model.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import pl.edu.agh.student.olemi.pl.edu.agh.student.olemi.model.entities.Ingredient;

@Dao
public interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIngredients(Ingredient... ingredients);

    @Insert
    void insertIngredient(Ingredient ingredient);
}
