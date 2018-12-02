package pl.edu.agh.student.olemi.pl.edu.agh.student.olemi.model.models;

import pl.edu.agh.student.olemi.pl.edu.agh.student.olemi.model.entities.Nutrients;

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
