package pl.edu.agh.student.olemi.models;

import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.student.olemi.entities.Nutrients;

public class ComplexProductModel implements ProductModel {

    private String name;

    private List<IngredientModel> ingredients = new LinkedList<IngredientModel>();

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Nutrients getNutrients() {
        return null;
    }

    public List<IngredientModel> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientModel> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(IngredientModel ingredient) {
        ingredients.add(ingredient);
    }
}
