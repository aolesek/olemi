package pl.edu.agh.student.olemi.model_maciek;

public class Nutrients {

    //in 100 grams
    public Double carbs;
    public Double protein;
    public Double fat;
    public Double calories;

    public Nutrients(Double carbs, Double protein, Double fat, Double calories) {
        this.carbs = carbs;
        this.protein = protein;
        this.fat = fat;
        this.calories = calories;
    }

    public Double getCarbs() {
        return carbs;
    }

    public void setCarbs(Double carbs) {
        this.carbs = carbs;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }
}
