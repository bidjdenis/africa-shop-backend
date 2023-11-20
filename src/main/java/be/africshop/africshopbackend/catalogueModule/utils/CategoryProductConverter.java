package be.africshop.africshopbackend.catalogueModule.utils;

import be.africshop.africshopbackend.catalogueModule.dto.ProductCategoryRequest;
import be.africshop.africshopbackend.catalogueModule.responses.ProductCategoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import be.africshop.africshopbackend.catalogueModule.entities.ProductCategory;


@Slf4j
@Component
public class CategoryProductConverter {

    public ProductCategory categorieProduitRequestToObject(ProductCategoryRequest request) {
        ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(request, productCategory);
        return productCategory;
    }


    public ProductCategory categorieProduitRequestToObject(ProductCategoryRequest request, ProductCategory productCategory) {
        BeanUtils.copyProperties(request, productCategory);
        return productCategory;
    }

    public ProductCategoryResponse objectToResponse(ProductCategory request){
        ProductCategoryResponse response = new ProductCategoryResponse();
        BeanUtils.copyProperties(request, response);
        return response;
    }
}
