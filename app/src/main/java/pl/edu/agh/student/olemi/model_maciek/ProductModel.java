package pl.edu.agh.student.olemi.model_maciek;

public abstract class ProductModel {

    String name;
    Nutrients nutrients;

    public ProductModel(String name, Nutrients nutrients) {
        this.name = name;
        this.nutrients = nutrients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Nutrients getNutrients() {
        return nutrients;
    }

    public void setNutrients(Nutrients nutrients) {
        this.nutrients = nutrients;
    }
}
