package pl.edu.agh.student.olemi.entities;

public final class NutrientsBuilder {

    public Integer calories;

    public Integer protein;

    public Integer carbohydrates;

    public Integer fats;

    private NutrientsBuilder() {
    }

    public static NutrientsBuilder aNutrients() {
        return new NutrientsBuilder();
    }

    public static NutrientsBuilder copyNutrients(Nutrients nutrients) {
        final NutrientsBuilder nutrientsBuilder = new NutrientsBuilder();
        nutrientsBuilder.calories = nutrients.calories;
        nutrientsBuilder.carbohydrates = nutrients.carbohydrates;
        nutrientsBuilder.fats = nutrients.fats;
        nutrientsBuilder.protein = nutrients.protein;
        return nutrientsBuilder;
    }

    public NutrientsBuilder withCalories(Integer calories) {
        this.calories = calories;
        return this;
    }

    public NutrientsBuilder withProtein(Integer protein) {
        this.protein = protein;
        return this;
    }

    public NutrientsBuilder withCarbohydrates(Integer carbohydrates) {
        this.carbohydrates = carbohydrates;
        return this;
    }

    public NutrientsBuilder withFats(Integer fats) {
        this.fats = fats;
        return this;
    }

    public Nutrients build() {
        Nutrients nutrients = new Nutrients();
        nutrients.fats = this.fats;
        nutrients.carbohydrates = this.carbohydrates;
        nutrients.protein = this.protein;
        nutrients.calories = this.calories;
        return nutrients;
    }
}