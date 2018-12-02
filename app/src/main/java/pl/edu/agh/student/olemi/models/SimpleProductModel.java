package pl.edu.agh.student.olemi.models;

import pl.edu.agh.student.olemi.entities.Nutrients;

public class SimpleProductModel implements ProductModel {

    final private String name;

    final private Nutrients nutrients;

    public SimpleProductModel(String name, Nutrients nutrients) {
        this.name = name;
        this.nutrients = nutrients;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Nutrients getNutrients() {
        return nutrients;
    }
}
