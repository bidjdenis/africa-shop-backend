package be.africshop.africshopbackend.catalogueModule.utils;


import be.africshop.africshopbackend.catalogueModule.dto.ProductRequest;
import be.africshop.africshopbackend.catalogueModule.entities.Product;
import be.africshop.africshopbackend.catalogueModule.responses.ProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductConverter {

    public Product productRequestToObject(ProductRequest request) {
        Product product = new Product();
        BeanUtils.copyProperties(request, product);
        return product;
    }


    public Product productRequestToObject(ProductRequest productRequest, Product Product) {
        BeanUtils.copyProperties(productRequest, Product);
        return Product;
    }

    public ProductResponse objectToResponse(Product request){
        ProductResponse response = new ProductResponse();
        response.setPublish(request.getPublish());
        response.setCountry(request.getCountry());
        BeanUtils.copyProperties(request, response);
        return response;
    }

}
