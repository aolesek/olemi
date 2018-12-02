package pl.edu.agh.student.olemi.model_maciek;

public class AppliedProduct {

    private ProductModel productModel;
    private Double weight;

    public AppliedProduct(ProductModel productModel, Double weight) {
        this.productModel = productModel;
        this.weight = weight;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public void setProductModel(ProductModel productModel) {
        this.productModel = productModel;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
