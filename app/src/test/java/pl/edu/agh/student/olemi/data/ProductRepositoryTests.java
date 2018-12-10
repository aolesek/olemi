package pl.edu.agh.student.olemi.data;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import pl.edu.agh.student.olemi.database.MockDatabase;
import pl.edu.agh.student.olemi.entities.NutrientsBuilder;
import pl.edu.agh.student.olemi.models.ComplexProductModel;
import pl.edu.agh.student.olemi.models.IngredientModel;
import pl.edu.agh.student.olemi.models.ProductModel;
import pl.edu.agh.student.olemi.models.SimpleProductModel;
import pl.edu.agh.student.olemi.repositories.NoDbProductRepository;
import pl.edu.agh.student.olemi.repositories.ProductRepository;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;

public class ProductRepositoryTests {

    private ProductRepository emptyRepo;
    private Context context;
    private SimpleProductModel exampleProduct1;
    private SimpleProductModel exampleProduct2;

    @Before
    public void prepareTest() {
        context = mock(Context.class);
        MockDatabase.reset();
        emptyRepo = new NoDbProductRepository(context, false);
        final SimpleProductModel exampleProduct1 = new SimpleProductModel("example product",
                NutrientsBuilder.aNutrients().withCalories(10d)
                        .withCarbohydrates(20d).withFats(30d).withProtein(40d).build());
        final SimpleProductModel exampleProduct2 = new SimpleProductModel("example product",
                NutrientsBuilder.aNutrients().withCalories(10d)
                        .withCarbohydrates(20d).withFats(30d).withProtein(40d).build());
    }

    @Test
    public void emptyDbTests() {
        // given

        // when
        final List<ProductModel> products = emptyRepo.getProductsWithLimit(100).blockingFirst();

        // then
        assertTrue("Database should be empty at first", products.isEmpty());
    }

    @Test
    public void singleItemInsertionTest() {
        // given

        // when
        emptyRepo.insertProduct(exampleProduct1).blockingGet();
        final List<ProductModel> products = emptyRepo.getProductsWithLimit(100).blockingFirst();

        // then
        assertEquals("Database should have one element", 1, products.size());
        assertTrue("Single element should be inserted product", products.contains(exampleProduct1));
    }

    @Test
    public void multiItemsInsertionTest() {
        // given

        // when
        emptyRepo.insertProduct(exampleProduct1).blockingGet();
        emptyRepo.insertProduct(exampleProduct2).blockingGet();
        final List<ProductModel> products = emptyRepo.getProductsWithLimit(100).blockingFirst();

        // then
        assertTrue("Database should have two elements", products.size() == 2);
        assertTrue("Elements should be inserted products", products.containsAll
                (Arrays.asList(exampleProduct1, exampleProduct2)));
    }

    @Test
    public void complexProductInsertionTest() {
        // given

        // when
        ComplexProductModel complex = new ComplexProductModel("complex 1");
        complex.addIngredient(new IngredientModel(0.3, exampleProduct1));
        complex.addIngredient(new IngredientModel(0.7, exampleProduct2));
        emptyRepo.insertProduct(complex).blockingGet();
        final List<ProductModel> products = emptyRepo.getProductsWithLimit(100).blockingFirst();

        // then
        assertTrue("Database should have one element", products.size() == 1);
        assertTrue("Single element should be inserted complex product", products.contains(complex));
    }

    @Test
    public void multipleProductsInsertionTest() {
        // given
        emptyRepo.insertProduct(new SimpleProductModel("simple 1", NutrientsBuilder.ones()))
                .blockingGet();
        emptyRepo.insertProduct(new SimpleProductModel("simple 2", NutrientsBuilder.ones()))
                .blockingGet();
        emptyRepo.insertProduct(new SimpleProductModel("simple 3", NutrientsBuilder.ones()))
                .blockingGet();
        emptyRepo.insertProduct(new SimpleProductModel("simple 4", NutrientsBuilder.ones()))
                .blockingGet();
        emptyRepo.insertProduct(new SimpleProductModel("simple 5", NutrientsBuilder.ones()))
                .blockingGet();
        emptyRepo.insertProduct(new SimpleProductModel("simple 6", NutrientsBuilder.ones()))
                .blockingGet();
        emptyRepo.insertProduct(new SimpleProductModel("simple 7", NutrientsBuilder.ones()))
                .blockingGet();
        emptyRepo.insertProduct(new SimpleProductModel("simple 8", NutrientsBuilder.ones()))
                .blockingGet();
        emptyRepo.insertProduct(new SimpleProductModel("simple 9", NutrientsBuilder.ones()))
                .blockingGet();
        emptyRepo.insertProduct(new SimpleProductModel("simple 10", NutrientsBuilder.ones()))
                .blockingGet();

        // when
        final List<ProductModel> products = emptyRepo.getProductsWithLimit(100).blockingFirst();
        final List<ProductModel> one = emptyRepo.getProductsWithLimit(1).blockingFirst();
        final List<ProductModel> seven = emptyRepo.getProductsWithLimit(7).blockingFirst();


        // then
        assertTrue("Should return lists of all 10 objects", products.size() == 10);
        assertTrue("Should return 1 object (limit = 1)", one.size() == 1);
        assertTrue("Should return 7 objects (limit = 7)", seven.size() == 7);
    }

    @Test
    public void searchTest() {
        // given
        emptyRepo.insertProduct(new SimpleProductModel("eggs", NutrientsBuilder.ones()))
                .blockingGet();
        emptyRepo.insertProduct(new SimpleProductModel("two eggs", NutrientsBuilder.ones()))
                .blockingGet();
        emptyRepo.insertProduct(new SimpleProductModel("milk", NutrientsBuilder.ones()))
                .blockingGet();
        emptyRepo.insertProduct(new SimpleProductModel("cola", NutrientsBuilder.ones()))
                .blockingGet();
        emptyRepo.insertProduct(new SimpleProductModel("pork chops", NutrientsBuilder.ones()))
                .blockingGet();
        emptyRepo.insertProduct(new SimpleProductModel("chicken", NutrientsBuilder.ones()))
                .blockingGet();
        emptyRepo.insertProduct(new SimpleProductModel("pasta", NutrientsBuilder.ones()))
                .blockingGet();
        emptyRepo.insertProduct(new SimpleProductModel("wine", NutrientsBuilder.ones()))
                .blockingGet();
        emptyRepo.insertProduct(new SimpleProductModel("white wine", NutrientsBuilder.ones()))
                .blockingGet();
        emptyRepo.insertProduct(new SimpleProductModel("red wine", NutrientsBuilder.ones()))
                .blockingGet();

        // when
        final List<ProductModel> wine = emptyRepo.serachProducts("wine").blockingFirst();
        final List<ProductModel> egg = emptyRepo.serachProducts("egg").blockingFirst();

        final List<String> wineNames = wine.stream().map(ProductModel::getName).collect(Collectors.toList());
        final List<String> eggNames = egg.stream().map(ProductModel::getName).collect(Collectors
                .toList());

        // then
        assertTrue("Should return 3 results for 'wine' phrase", wine.size() == 3);
        assertTrue("Should return wine, red wine and white wine for wine phrase", wineNames
                .containsAll(Arrays.asList("wine", "red wine", "white wine")));

        assertTrue("Should return 2 results for 'egg' phrase", egg.size() == 2);
        assertTrue("Should return egg and two eggs for egg phrase", eggNames
                .containsAll(Arrays.asList("eggs", "two eggs")));
    }
}
