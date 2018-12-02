package pl.edu.agh.student.olemi.models;

import android.icu.util.Calendar;

import pl.edu.agh.student.olemi.entities.Product;

public class MealModel {

    private Calendar day;

    private ProductModel product;

    private Double weight;

    public MealModel(Calendar day, ProductModel product, Double weight) {
        this.day = day;
        this.product = product;
        this.weight = weight;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Calendar getDay() {
        return day;
    }

    public void setDay(Calendar day) {
        this.day = day;
    }
}
