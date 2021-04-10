package Lager.API.Demo.Controller;
// översta lagret
//pratar med service

import Lager.API.Demo.Service.ProductService;
import Lager.API.Demo.exception.NotFoundException;
import Lager.API.Demo.model.request.ProductDetailsRequestModel;
import Lager.API.Demo.model.response.ProductResponseModel;
import Lager.API.Demo.shared.dto.ProductDto;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController // ansvara för allt på localhost
@RequestMapping("products") //localhost:8080/products//Mappar controller klassen till products
public class UserController {

    //injektar userService

    // constructor istället för @Autowired injektion
    private final ProductService productService;

    public UserController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping// Hämtar alla produkter
    public List<ProductResponseModel> getProducts() {

        List<ProductDto> productDtos = productService.getProducts();
        ArrayList<ProductResponseModel> responseList = new ArrayList<>();

        for (ProductDto productDto : productDtos){
            ProductResponseModel responseModel = new ProductResponseModel();
            BeanUtils.copyProperties(productDto,responseModel);
            responseList.add(responseModel);
        }
            return responseList;

    }


    @GetMapping(value = "/{id}")// ansvarar för get
    public ProductResponseModel getProduct(@PathVariable String id) {

        ProductResponseModel responseModel = new ProductResponseModel();
        Optional<ProductDto> optionalProductDto = productService.getProductById(id);
        if (optionalProductDto.isPresent()) {
            ProductDto productDto = optionalProductDto.get();
            BeanUtils.copyProperties(productDto, responseModel);
            return responseModel;
        }
        throw new NotFoundException("No product with id " + id);


    }

    @PostMapping// ansvarar för post
    public ResponseEntity<ProductResponseModel> createProduct(@RequestBody ProductDetailsRequestModel productDetailsModel) {
        //Copy jason to dto in
        ProductDto productDtoIn = new ProductDto();
        BeanUtils.copyProperties(productDetailsModel, productDtoIn);
        // Pass dto in to service layer
        ProductDto productDtoOut = productService.createProduct(productDtoIn);
        //copy dto out from service layer to response
        ProductResponseModel response = new ProductResponseModel();
        BeanUtils.copyProperties(productDtoOut, response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")// ansvarar för update
    public ProductResponseModel updateProduct(@PathVariable String id, @RequestBody ProductDetailsRequestModel requestData) {

        //Copy jason to dto in
        ProductDto productDtoIn = new ProductDto();
        BeanUtils.copyProperties(requestData, productDtoIn);
        // Pass dto in to service layer
        Optional<ProductDto> productDtoOut = productService.updateProduct(id, productDtoIn);
        if (productDtoOut.isEmpty()) {
            throw new NotFoundException("No product with id " + id);
        }

        ProductDto productDto = productDtoOut.get();
        ProductResponseModel responseModel = new ProductResponseModel();
        BeanUtils.copyProperties(productDto, responseModel);
        return responseModel;


    }

    @DeleteMapping("/{id}")// ansvarar för delete
    String deleteProduct(@PathVariable String id) {

        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return "";
        }
        throw new NotFoundException("No product with id " + id);


    }


}



