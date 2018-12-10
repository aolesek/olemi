package pl.edu.agh.student.olemi.models;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleProductModel that = (SimpleProductModel) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(nutrients, that.nutrients);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, nutrients);
    }
}
