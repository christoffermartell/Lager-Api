package Lager.API.Demo.repository;

import Lager.API.Demo.repository.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
    //Optional<ProductEntity> findByProductId(String product_Id);
    Optional<ProductEntity> findByName(String name);

    Optional<ProductEntity> findByProductId(String name);


    long deleteByProductId(String productId);


}
