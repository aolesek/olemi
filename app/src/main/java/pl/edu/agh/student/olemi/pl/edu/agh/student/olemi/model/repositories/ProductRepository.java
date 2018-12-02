package pl.edu.agh.student.olemi.pl.edu.agh.student.olemi.model.repositories;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import pl.edu.agh.student.olemi.pl.edu.agh.student.olemi.model.entities.Product;
import pl.edu.agh.student.olemi.pl.edu.agh.student.olemi.model.models.ComplexProductModel;
import pl.edu.agh.student.olemi.pl.edu.agh.student.olemi.model.models.ProductModel;
import pl.edu.agh.student.olemi.pl.edu.agh.student.olemi.model.models.SimpleProductModel;

public interface ProductRepository {

    public Completable insertProduct(ProductModel product);

    public Completable insertSimpleProduct(SimpleProductModel simpleProduct);

    public Completable insertComplexProduct(ComplexProductModel complexProduct);

    public Flowable<List<ProductModel>> getProductsWithLimit(long limit);

    public Flowable<List<ProductModel>> serachProducts(String phrase);
}
