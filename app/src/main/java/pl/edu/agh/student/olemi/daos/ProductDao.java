package pl.edu.agh.student.olemi.daos;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import pl.edu.agh.student.olemi.entities.Product;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertProducts(List<Product> products); //TODO: Maybe maybe?

    @Insert
    Completable insertProduct(Product product);

    @Query("select * from Product limit 10")
    Flowable<List<Product>> getProducts();
}
