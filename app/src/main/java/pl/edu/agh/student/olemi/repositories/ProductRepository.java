package pl.edu.agh.student.olemi.repositories;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import pl.edu.agh.student.olemi.models.ComplexProductModel;
import pl.edu.agh.student.olemi.models.ProductModel;
import pl.edu.agh.student.olemi.models.SimpleProductModel;

public interface ProductRepository {

    public Completable insertProduct(ProductModel product);

    public Completable insertSimpleProduct(SimpleProductModel simpleProduct);

    public Completable insertComplexProduct(ComplexProductModel complexProduct);

    public Flowable<List<ProductModel>> getProductsWithLimit(long limit);

    public Flowable<List<ProductModel>> serachProducts(String phrase);
}
