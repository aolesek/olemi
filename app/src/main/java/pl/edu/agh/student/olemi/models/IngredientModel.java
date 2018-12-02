package pl.edu.agh.student.olemi.models;

public class IngredientModel {

    private Double partOfTotalWeight;

    private ProductModel product;

    public IngredientModel(Double partOfTotalWeight, ProductModel product) {
        this.partOfTotalWeight = partOfTotalWeight;
        this.product = product;
    }

    public Double getPartOfTotalWeight() {
        return partOfTotalWeight;
    }

    public void setPartOfTotalWeight(Double partOfTotalWeight) {
        this.partOfTotalWeight = partOfTotalWeight;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }
}
