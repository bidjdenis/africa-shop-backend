package be.africshop.africshopbackend.catalogueModule.services.impls;

import be.africshop.africshopbackend.catalogueModule.dto.ProductCategoryRequest;
import be.africshop.africshopbackend.catalogueModule.entities.ProductCategory;
import be.africshop.africshopbackend.catalogueModule.repository.ProductCategoryRepository;
import be.africshop.africshopbackend.catalogueModule.repository.ProductRepository;
import be.africshop.africshopbackend.catalogueModule.responses.ProductCategoryResponse;
import be.africshop.africshopbackend.catalogueModule.services.ProductCategoryService;
import be.africshop.africshopbackend.catalogueModule.utils.CategoryProductConverter;
import be.africshop.africshopbackend.utils.DataStatus;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

   private final ProductCategoryRepository repository;

   private final CategoryProductConverter converter;

    @Override
    @SneakyThrows
    @Transactional
    public ProductCategoryResponse addCategorieProduitRequest(ProductCategoryRequest request){
        return Optional.of(request).stream()
                .map(converter::categorieProduitRequestToObject)
                .peek(requestProductCategory -> requestProductCategory.setCodeAuto(RandomStringUtils.randomAlphanumeric(10).toUpperCase()))
                .peek(repository::save)
                .map(converter::objectToResponse)
                .findFirst()
                .orElseThrow(() -> new Exception("Error while saving Category Product"));
    }

    @Override
    @SneakyThrows
    @Transactional
    public ProductCategoryResponse updateCategorieProduitRequest(ProductCategoryRequest request, Long id)  {
        ProductCategory productCategory = repository.findProductCategoryByDataStatusIsNotAndId(DataStatus.DELETED, id).orElseThrow();
        productCategory.setDataStatus(DataStatus.UPDATED);
        return Optional.of(request)
                .map(requestCategery -> converter.categorieProduitRequestToObject(request, productCategory))
                .map(repository::save)
                .map(converter::objectToResponse)
                .orElseThrow(() -> new Exception("Category Product not found"));
    }

    @Override
    @SneakyThrows
    @Transactional
    public ProductCategoryResponse deleteCategorieProduitRequest(Long id) {
        return  repository.findProductCategoryByDataStatusIsNotAndId(DataStatus.DELETED, id).stream()
                .peek(categoriesProduct -> categoriesProduct.setDataStatus(DataStatus.DELETED))
                .map(repository::save)
                .map(converter::objectToResponse)
                .findFirst()
                .orElseThrow(()-> new Exception("Category product not found!"));
    }

    @Override
    @SneakyThrows
    @Transactional(readOnly = true)
    public ProductCategoryResponse getCategorieProduitRequestById(Long id) {
        return repository.findProductCategoryByDataStatusIsNotAndId(DataStatus.DELETED, id)
                .map(converter::objectToResponse)
                .orElseThrow(() -> new Exception("Category product not found!"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductCategoryResponse> getAllCategorieProduitRequests() {
        return repository.findProductCategoryByDataStatusIsNotOrderByLibelle(DataStatus.DELETED).stream()
                .map(converter::objectToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductCategoryResponse> searchProductCategroy(String name) {

        System.out.println("*************************");
        System.out.println("Libelle : "+name);
        System.out.println("**************************");
        List<ProductCategoryResponse> categoryResponses = new ArrayList<>();
        List<ProductCategory> productCategoryList = repository.findByDataStatusIsNotAndLibelleIgnoreCase(DataStatus.DELETED, name);

        System.out.println("**********************************************");
        System.out.println(productCategoryList);
        System.out.println("**********************************************");

        for (ProductCategory p : productCategoryList) {
            ProductCategoryResponse response = new ProductCategoryResponse();
            BeanUtils.copyProperties(p, response);
            categoryResponses.add(response);
        }

        return categoryResponses;
    }
}
