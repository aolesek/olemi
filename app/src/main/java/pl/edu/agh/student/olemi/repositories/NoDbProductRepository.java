package pl.edu.agh.student.olemi.repositories;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import pl.edu.agh.student.olemi.database.MockDatabase;
import pl.edu.agh.student.olemi.models.ComplexProductModel;
import pl.edu.agh.student.olemi.models.ProductModel;
import pl.edu.agh.student.olemi.models.SimpleProductModel;

public class NoDbProductRepository implements ProductRepository {

    final MockDatabase mockDatabase;

    public NoDbProductRepository(Context context) {
        this.mockDatabase= MockDatabase.getInstance(context);
    }

    @Override
    public Completable insertProduct(ProductModel product) {
        return Completable.fromRunnable(() -> mockDatabase.availableProducts.add(product));
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
        return Flowable.just(mockDatabase.availableProducts.stream().limit(limit).collect(Collectors.toList()));
    }

    @Override
    public Flowable<List<ProductModel>> serachProducts(String phrase) {
        return Flowable.just(mockDatabase.availableProducts.stream().filter(productModel -> productModel.getName().contains(phrase)).collect(Collectors.toList()));
    }
}
