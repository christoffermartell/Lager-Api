package Lager.API.Demo.shared.dto;
// DTO = Data transfer object
// det som skickas mellan layers..

import java.io.Serializable;

public class ProductDto implements Serializable {

    private long id;
    private String productId;
    private String name;
    private Integer cost;
    private String category;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



}
