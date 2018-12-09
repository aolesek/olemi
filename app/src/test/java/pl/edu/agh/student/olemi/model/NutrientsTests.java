package pl.edu.agh.student.olemi.model;

import org.junit.Test;

import pl.edu.agh.student.olemi.entities.Nutrients;
import pl.edu.agh.student.olemi.entities.NutrientsBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class NutrientsTests {

    public static final Double DELTA = 0.01;

    @Test
    public void builderTest() {
        // given
        final Nutrients exampleNutrients = NutrientsBuilder.aNutrients().withCalories(100d)
                .withCarbohydrates(120d)
                .withFats(140d)
                .withProtein(160d)
                .build();
        final Nutrients copy = NutrientsBuilder.copyNutrients(exampleNutrients).build();

        // when

        // then
        assertEquals(100d, exampleNutrients.calories, DELTA);
        assertEquals(120d,  exampleNutrients.carbohydrates, DELTA);
        assertEquals(140d,  exampleNutrients.fats, DELTA);
        assertEquals(160d,  exampleNutrients.protein, DELTA);
        assertEquals(100d,  copy.calories, DELTA);
        assertEquals(120d,  copy.carbohydrates, DELTA);
        assertEquals(140d,  copy.fats, DELTA);
        assertEquals(160d,  copy.protein, DELTA);
    }

    @Test
    public void multiplyingTest() {
        // given
        final Nutrients exampleNutrients = NutrientsBuilder.aNutrients().withCalories(100d)
                .withCarbohydrates(120d)
                .withFats(140d)
                .withProtein(160d)
                .build();
        final Nutrients originalValues = NutrientsBuilder.copyNutrients(exampleNutrients).build();

        // when
        final Nutrients multipiedByInteger = exampleNutrients.multiplyBy(2.0);
        final Nutrients multipliedByDouble = exampleNutrients.multiplyBy(2.5);

        // then
        assertEquals("Original values should be immutable", originalValues, exampleNutrients);

        assertEquals(200,  multipiedByInteger.calories, DELTA);
        assertEquals(240,  multipiedByInteger.carbohydrates, DELTA);
        assertEquals(280,  multipiedByInteger.fats, DELTA);
        assertEquals(320,  multipiedByInteger.protein, DELTA);

        assertEquals(250,  multipliedByDouble.calories, DELTA);
        assertEquals(300,  multipliedByDouble.carbohydrates, DELTA);
        assertEquals(350,  multipliedByDouble.fats, DELTA);
        assertEquals(400,  multipliedByDouble.protein, DELTA);
    }

    @Test
    public void additionTest() {
        // given
        final Nutrients n1 = NutrientsBuilder.aNutrients().withCalories(100d)
                .withCarbohydrates(120d)
                .withFats(140d)
                .withProtein(160d)
                .build();
        final Nutrients n1original = NutrientsBuilder.copyNutrients(n1).build();

        final Nutrients n2 = NutrientsBuilder.aNutrients().withCalories(200d)
                .withCarbohydrates(220d)
                .withFats(240d)
                .withProtein(260d)
                .build();
        final Nutrients n2original = NutrientsBuilder.copyNutrients(n2).build();

        final Nutrients n3 = NutrientsBuilder.aNutrients().withCalories(300d)
                .withCarbohydrates(320d)
                .withFats(340d)
                .withProtein(360d)
                .build();
        final Nutrients n3original = NutrientsBuilder.copyNutrients(n3).build();

        final Nutrients n4 = NutrientsBuilder.aNutrients().withCalories(400d)
                .withCarbohydrates(420d)
                .withFats(440d)
                .withProtein(460d)
                .build();
        final Nutrients n4original = NutrientsBuilder.copyNutrients(n4).build();

        // when
        final Nutrients sum = Nutrients.sumOf(n1, n2, n3, n4);

        // then
        assertEquals("Original values should be immutable", n1original, n1);
        assertEquals("Original values should be immutable", n2original, n2);
        assertEquals("Original values should be immutable", n3original, n3);
        assertEquals("Original values should be immutable", n4original, n4);

        assertEquals(1000,  sum.calories, DELTA);
        assertEquals(1080,  sum.carbohydrates, DELTA);
        assertEquals(1160,  sum.fats, DELTA);
        assertEquals(1240,  sum.protein, DELTA);
    }
}
