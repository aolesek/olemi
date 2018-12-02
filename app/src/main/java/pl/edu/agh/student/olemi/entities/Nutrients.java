package pl.edu.agh.student.olemi.entities;

import java.util.stream.Stream;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Nutrients {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    public Integer calories;

    public Integer protein;

    public Integer carbohydrates;

    public Integer fats;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void add(Nutrients otherNutrients) {
        this.calories += otherNutrients.calories;
        this.protein += otherNutrients.protein;
        this.carbohydrates += otherNutrients.carbohydrates;
        this.fats += otherNutrients.fats;
    }

    public void multiplyBy(Double factor) {
        this.calories = (int) (factor * this.calories);
        this.protein = (int) (factor * this.protein);
        this.carbohydrates = (int) (factor * this.carbohydrates);
        this.fats = (int) (factor * this.fats);
    }

    public static Nutrients sumOf(Nutrients... nutrients) {
        final Nutrients result = NutrientsBuilder.aNutrients().withCalories(0).withCarbohydrates(0).withFats(0).withProtein(0).build();
        Stream.of(nutrients).forEach(result::add);
        return result;
    }
}
