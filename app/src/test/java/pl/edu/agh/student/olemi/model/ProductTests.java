package pl.edu.agh.student.olemi.model;

import android.icu.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.edu.agh.student.olemi.entities.Nutrients;
import pl.edu.agh.student.olemi.entities.NutrientsBuilder;
import pl.edu.agh.student.olemi.models.ComplexProductModel;
import pl.edu.agh.student.olemi.models.IngredientModel;
import pl.edu.agh.student.olemi.models.MealModel;
import pl.edu.agh.student.olemi.models.ProductModel;
import pl.edu.agh.student.olemi.models.SimpleProductModel;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ProductTests {

    private Nutrients nutrients1;
    private ProductModel simpleProduct1;
    private int hash1;
    private int nhash1;

    private Nutrients nutrients2;
    private ProductModel simpleProduct2;
    private int hash2;
    private int nhash2;

    private Nutrients nutrients3;
    private ProductModel simpleProduct3;
    private int hash3;
    private int nhash3;

    @Mock
    Calendar calendar;

    private ComplexProductModel complexProduct;

    @Before
    public void prepareData() {
        nutrients1 = NutrientsBuilder.aNutrients().withCalories(100d).withCarbohydrates(101d).withFats(102d).withProtein(103d).build();
        simpleProduct1 = new SimpleProductModel("product 1", nutrients1);
        hash1 = simpleProduct1.hashCode();
        nhash1 = nutrients1.hashCode();

        nutrients2 = NutrientsBuilder.aNutrients().withCalories(200d).withCarbohydrates(201d).withFats(202d).withProtein(203d).build();
        simpleProduct2 = new SimpleProductModel("product 2", nutrients2);
        hash2 = simpleProduct2.hashCode();
        nhash2 = nutrients2.hashCode();

        nutrients3 = NutrientsBuilder.aNutrients().withCalories(300d).withCarbohydrates(301d).withFats(302d).withProtein(303d).build();
        simpleProduct3 = new SimpleProductModel("product 3", nutrients3);
        hash3 = simpleProduct3.hashCode();
        nhash3 = nutrients3.hashCode();

        complexProduct = new ComplexProductModel("complex product");
        complexProduct.addIngredient(new IngredientModel(0.2, simpleProduct1));
        complexProduct.addIngredient(new IngredientModel(0.3, simpleProduct2));
        complexProduct.addIngredient(new IngredientModel(0.5, simpleProduct3));
    }

    @Test
    public void singleProductTest() {
        // given

        // when

        // then
        assertEquals(nutrients1, simpleProduct1.getNutrients());
        assertEquals(nutrients1, simpleProduct1.getNutrients());
        assertEquals(nutrients1, simpleProduct1.getNutrients());
    }

    @Test
    public void complexProductTest() {
        // given
        final ComplexProductModel complex1 = new ComplexProductModel("complex 1");
        complex1.addIngredient(new IngredientModel(0.2, simpleProduct1));
        complex1.addIngredient(new IngredientModel(0.3, simpleProduct2));
        complex1.addIngredient(new IngredientModel(0.5, simpleProduct3));

        final double calories = 0.2 * 100 + 0.3 * 200 + 0.5 * 300;
        final double carbohydrates = 0.2 * 101 + 0.3 * 201 + 0.5 * 301;
        final double fats = 0.2 * 102 + 0.3 * 202 + 0.5 * 302;
        final double proteins = 0.2 * 103 + 0.3 * 203 + 0.5 * 303;
        final Nutrients expectedNutrients = NutrientsBuilder.aNutrients().withCalories(calories).withCarbohydrates(carbohydrates).withFats(fats).withProtein(
                proteins).build();

        // when
        final Nutrients complexProductsNutrients = complex1.getNutrients();

        // then
        assertEquals("Original nutrients should not be mutated", nutrients1.hashCode(), nhash1);
        assertEquals("Original nutrients should not be mutated", nutrients2.hashCode(), nhash2);
        assertEquals("Original nutrients should not be mutated", nutrients3.hashCode(), nhash3);

        assertEquals("Original simple products should not be mutated", simpleProduct1.hashCode(), hash1);
        assertEquals("Original simple products should not be mutated", simpleProduct2.hashCode(), hash2);
        assertEquals("Original simple products should not be mutated", simpleProduct3.hashCode(), hash3);

        assertEquals(expectedNutrients, complexProductsNutrients);
    }

    @Test
    public void mealTest() {
        // given
        final MealModel simpleProductMeal = new MealModel(null, simpleProduct1, 100d);
        final MealModel complexProductMeal100 = new MealModel(null, complexProduct, 100d);
        final Nutrients complexPer100g = complexProductMeal100.getNutrients();
        final MealModel complexProductMeal = new MealModel(null, complexProduct, 768d);
        // when
        final Nutrients nutrients = simpleProductMeal.getNutrients();
        final Nutrients complexNutrients = complexProductMeal.getNutrients();
        final Nutrients multipliedNutrients = complexPer100g.multiplyBy(7.68);
        // then
        assertEquals(nutrients, nutrients1);
        assertEquals(multipliedNutrients, complexNutrients);
    }
}
