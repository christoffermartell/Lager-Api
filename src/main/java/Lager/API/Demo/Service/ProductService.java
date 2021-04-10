package Lager.API.Demo.Service;

import Lager.API.Demo.shared.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    String getProduct();
    ProductDto createProduct(ProductDto productDetails);
    Optional<ProductDto> updateProduct(String id, ProductDto productDtoIn);
    boolean deleteProduct(String id);

    Optional<ProductDto> getProductById(String id);

    List<ProductDto> getProducts();
}
