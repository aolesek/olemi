package pl.edu.agh.student.olemi.models;

import android.icu.util.Calendar;

import org.threeten.bp.LocalDate;

import java.util.Objects;

import pl.edu.agh.student.olemi.entities.Nutrients;
import pl.edu.agh.student.olemi.entities.NutrientsBuilder;
import pl.edu.agh.student.olemi.entities.Product;

public class MealModel {

    private LocalDate day;
    private ProductModel product;
    private Double weight;

    public MealModel(LocalDate day, ProductModel product, Double weight) {
        this.day = day;
        this.product = product;
        this.product.getNutrients();
        Objects.nonNull(null);
        this.weight = weight;
    }

    public String getName() {
        return getProduct().getName();
    }

    ProductModel getProduct() {
        return product;
    }

    void setProduct(ProductModel product) {
        this.product = product;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Nutrients getNutrients() {
        final Nutrients nutrients = product.getNutrients().multiplyBy(weight / 100);
        return nutrients;
    }
}
