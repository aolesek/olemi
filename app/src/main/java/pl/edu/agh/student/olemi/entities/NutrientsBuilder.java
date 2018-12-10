package pl.edu.agh.student.olemi.entities;

public final class NutrientsBuilder {

    public Double calories = 0d;

    public Double protein = 0d;

    public Double carbohydrates = 0d;

    public Double fats = 0d;

    private NutrientsBuilder() {
    }

    public static NutrientsBuilder aNutrients() {
        return new NutrientsBuilder();
    }

    public static Nutrients ones() {
        return NutrientsBuilder.aNutrients().withCalories(1d).withProtein(1d).withCarbohydrates
                (1d).withFats(1d).build();
    }

    public static NutrientsBuilder copyNutrients(Nutrients nutrients) {
        final NutrientsBuilder nutrientsBuilder = new NutrientsBuilder();
        nutrientsBuilder.calories = nutrients.calories;
        nutrientsBuilder.carbohydrates = nutrients.carbohydrates;
        nutrientsBuilder.fats = nutrients.fats;
        nutrientsBuilder.protein = nutrients.protein;
        return nutrientsBuilder;
    }

    public NutrientsBuilder withCalories(Double calories) {
        this.calories = calories;
        return this;
    }

    public NutrientsBuilder withProtein(Double protein) {
        this.protein = protein;
        return this;
    }

    public NutrientsBuilder withCarbohydrates(Double carbohydrates) {
        this.carbohydrates = carbohydrates;
        return this;
    }

    public NutrientsBuilder withFats(Double fats) {
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