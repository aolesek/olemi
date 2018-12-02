package pl.edu.agh.student.olemi.model_maciek;

import java.util.List;

public class ComplexProduct extends ProductModel {

    List<Pair> ingriedients;
    Double totalWeight;

    public ComplexProduct(String name, Nutrients nutrients, List<Pair> ingriedients, Double totalWeight) {
        super(name, nutrients);
        this.ingriedients = ingriedients;
        this.totalWeight = totalWeight;
    }
}
