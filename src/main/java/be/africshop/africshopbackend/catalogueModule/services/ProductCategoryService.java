package be.africshop.africshopbackend.catalogueModule.services;

import be.africshop.africshopbackend.catalogueModule.dto.ProductCategoryRequest;
import be.africshop.africshopbackend.catalogueModule.responses.ProductCategoryResponse;

import java.util.List;

public interface ProductCategoryService {

    ProductCategoryResponse addCategorieProduitRequest(ProductCategoryRequest request);

    ProductCategoryResponse updateCategorieProduitRequest(ProductCategoryRequest request, Long id);

    ProductCategoryResponse deleteCategorieProduitRequest(Long id);

    ProductCategoryResponse getCategorieProduitRequestById(Long id);

    List<ProductCategoryResponse> getAllCategorieProduitRequests();
    List<ProductCategoryResponse> searchProductCategroy(String name);



}
