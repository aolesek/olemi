package pl.edu.agh.student.olemi.models;

import android.icu.util.Calendar;

import pl.edu.agh.student.olemi.entities.Product;

public class MealModel {

    private Calendar day;

    private Product product;

    private Double weight;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
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
