package pl.edu.agh.student.olemi.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import pl.edu.agh.student.olemi.models.ComplexProductModel;
import pl.edu.agh.student.olemi.models.ProductModel;
import pl.edu.agh.student.olemi.models.SimpleProductModel;

public class NoDbProductRepository implements ProductRepository {

    private final List<ProductModel> availableProducts = new ArrayList<>();

    @Override
    public Completable insertProduct(ProductModel product) {
        availableProducts.add(product);
        return Completable.fromRunnable(() -> availableProducts.add(product));
    }

    @Override
    public Completable insertSimpleProduct(SimpleProductModel simpleProduct) {
        return insertProduct(simpleProduct);
    }

    @Override
    public Completable insertComplexProduct(ComplexProductModel complexProduct) {
        return insertProduct(complexProduct);
    }

    @Override
    public Flowable<List<ProductModel>> getProductsWithLimit(long limit) {
        return Flowable.just(availableProducts.stream().limit(10).collect(Collectors.toList()));
    }

    @Override
    public Flowable<List<ProductModel>> serachProducts(String phrase) {
        return Flowable.just(availableProducts.stream().filter(productModel -> productModel.getName().contains(phrase)).collect(Collectors.toList()));
    }
}
