package pl.edu.agh.student.olemi.model_maciek;

public class Pair<Double, ProductModel> {
    private Double grams;
    private ProductModel product;
    public Pair(Double grams, ProductModel product){
        this.grams = grams;
        this.product = product;
    }
    public Double getGrams(){ return grams; }
    public ProductModel getProduct(){ return product; }
    public void setL(Double grams){ this.grams = grams; }
    public void setR(ProductModel product){ this.product = product; }
}
