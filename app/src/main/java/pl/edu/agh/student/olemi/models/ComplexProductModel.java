package pl.edu.agh.student.olemi.models;

import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.student.olemi.entities.Nutrients;
import pl.edu.agh.student.olemi.entities.NutrientsBuilder;

public class ComplexProductModel implements ProductModel {

    private String name;

    private List<IngredientModel> ingredients = new LinkedList<IngredientModel>();

    private Double defaultWeight;

    public ComplexProductModel(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Nutrients getNutrients() {
        final List<Nutrients> allNutrients = new LinkedList<>();
        ingredients.forEach(ingredient -> {
            Double partOfTotalWeight = ingredient.getPartOfTotalWeight();
            Nutrients nutrients = NutrientsBuilder.copyNutrients(ingredient.getProduct().getNutrients()).build();
            final Nutrients multiplied = nutrients.multiplyBy(partOfTotalWeight);
            allNutrients.add(multiplied);
        });
        return Nutrients.sumOf(allNutrients.toArray(new Nutrients[0]));
    }

    public List<IngredientModel> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientModel> ingredients) {
        this.ingredients = ingredients;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addIngredient(IngredientModel ingredient) {
        ingredients.add(ingredient);
    }

    public Double getDefaultWeight() {
        return defaultWeight;
    }

    public void setDefaultWeight(Double defaultWeight) {
        this.defaultWeight = defaultWeight;
    }
}
