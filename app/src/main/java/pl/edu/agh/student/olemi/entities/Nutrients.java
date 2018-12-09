package pl.edu.agh.student.olemi.entities;

import java.util.Objects;
import java.util.stream.Stream;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Nutrients {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    public Double calories;

    public Double protein;

    public Double carbohydrates;

    public Double fats;

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

    public Nutrients multiplyBy(Double factor) {
        Nutrients nutrients = NutrientsBuilder.copyNutrients(this).build();
        nutrients.calories = factor * this.calories;
        nutrients.protein = factor * this.protein;
        nutrients.carbohydrates = factor * this.carbohydrates;
        nutrients.fats = factor * this.fats;
        return nutrients;
    }

    public static Nutrients sumOf(Nutrients... nutrients) {
        final Nutrients result = NutrientsBuilder.aNutrients().withCalories(0d).withCarbohydrates(0d).withFats(0d).withProtein(0d).build();
        Stream.of(nutrients).forEach(result::add);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nutrients nutrients = (Nutrients) o;
        return Objects.equals(calories, nutrients.calories) &&
                Objects.equals(protein, nutrients.protein) &&
                Objects.equals(carbohydrates, nutrients.carbohydrates) &&
                Objects.equals(fats, nutrients.fats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calories, protein, carbohydrates, fats);
    }

    @Override
    public String toString() {
        return "Nutrients{" +
                "id=" + id +
                ", calories=" + calories +
                ", protein=" + protein +
                ", carbohydrates=" + carbohydrates +
                ", fats=" + fats +
                '}';
    }
}
