package Lager.API.Demo.Service.Impl;
//service pratar med repository

import Lager.API.Demo.Service.ProductService;
import Lager.API.Demo.repository.ProductRepository;
import Lager.API.Demo.repository.entity.ProductEntity;
import Lager.API.Demo.shared.Util;
import Lager.API.Demo.shared.dto.ProductDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    // constructor istället för @Autowired injektion
    private final ProductRepository productRepository;
    private Util util;

    public ProductServiceImpl(ProductRepository productRepository, Util util) {
        this.productRepository = productRepository;
        this.util = util;
    }

    @Override
    public String getProduct() {
        return "getProduct";
    }

    @Override // skapar en optional dto och returnerar
    public Optional<ProductDto> getProductById(String id) {

        Optional<ProductEntity> productIdEntity = productRepository.findByProductId(id);

        return productIdEntity.map(productEntity -> {
            ProductDto productDto = new ProductDto();
            BeanUtils.copyProperties(productEntity, productDto);

            return productDto;

        });
    }

    @Override
    public List<ProductDto> getProducts() {
       Iterable<ProductEntity> productEntities = productRepository.findAll();
        ArrayList<ProductDto> productDtos = new ArrayList<>();
       for (ProductEntity productEntity : productEntities){
           ProductDto productDto = new ProductDto();
           BeanUtils.copyProperties(productEntity, productDto);
           productDtos.add(productDto);
       }

        return productDtos;
    }

    @Override
    public ProductDto createProduct(ProductDto productDetailsIn) {

        Optional<ProductEntity> checkNameEntity = productRepository.findByName(productDetailsIn.getName());
        if (checkNameEntity.isPresent()) {

            throw new RuntimeException("Product already exists");

        }
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productDetailsIn, productEntity);

        // generera en hashkod som product_id
        String productId = util.generateHash(productDetailsIn.getName());
        productEntity.setProductId(productId.substring(3));

        // sparar våran nya data
        ProductEntity productEntityOut = productRepository.save(productEntity);
        ProductDto productDtoOut = new ProductDto();

        BeanUtils.copyProperties(productEntityOut, productDtoOut);


        return productDtoOut;
    }


    public Optional<ProductDto> updateProduct(String id, ProductDto productDto) {

        Optional<ProductEntity> productIdEntity = productRepository.findByProductId(id);
        if (productIdEntity.isEmpty())
            return Optional.empty();

        return productIdEntity.map(productEntity -> {
            ProductDto response = new ProductDto();
         //   BeanUtils.copyProperties(productDto, productEntity);
            // set all non-null properties to entity
            productEntity.setProductId(productDto.getProductId() !=null ? util.generateHash(productDto.getName()).substring(3) : productEntity.getProductId());
            productEntity.setName(productDto.getName()!=null ? productDto.getName() : productEntity.getName());
            productEntity.setCategory(productDto.getCategory()!=null ? productDto.getCategory() : productEntity.getCategory());
            productEntity.setCost(productDto.getCost() !=null  ? productDto.getCost() : productEntity.getCost());


            ProductEntity updatedProductEntity = productRepository.save(productEntity);
            BeanUtils.copyProperties(updatedProductEntity, response);
            return response;


        });

    }

    @Transactional
    public boolean deleteProduct(String id) {

      Long removedProductCount = productRepository.deleteByProductId(id);

      return removedProductCount >0;


    }


}