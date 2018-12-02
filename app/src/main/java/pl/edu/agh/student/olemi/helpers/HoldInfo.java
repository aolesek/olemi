package pl.edu.agh.student.olemi.helpers;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.student.olemi.models.IngredientModel;

public class HoldInfo {

    int mealNr = 0;

    List<IngredientModel> ingredientModelList = new ArrayList<>();

    public List<IngredientModel> getIngredientModelList() {
        return ingredientModelList;
    }

    public int getMealNr() {
        return mealNr;
    }

    public void setMealNr(int mealNr) {
        this.mealNr = mealNr;
    }

    public void setIngredientModelList(List<IngredientModel> ingredientModelList) {
        this.ingredientModelList = ingredientModelList;
    }

    private static final HoldInfo instance = new HoldInfo();

    private HoldInfo(){}

    public static HoldInfo getInstance(){ return instance; }
}
