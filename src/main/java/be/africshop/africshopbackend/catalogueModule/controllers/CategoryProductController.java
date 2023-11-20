package be.africshop.africshopbackend.catalogueModule.controllers;


import be.africshop.africshopbackend.catalogueModule.dto.ProductCategoryRequest;
import be.africshop.africshopbackend.catalogueModule.services.ProductCategoryService;
import be.africshop.africshopbackend.utils.JavaUtils;
import be.africshop.africshopbackend.utils.handlers.HandlerException;
import be.africshop.africshopbackend.utils.responses.HttpResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/v1/product_category/")
public class CategoryProductController extends HandlerException {



    private final ProductCategoryService productCategoryService;

    public CategoryProductController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @PostMapping(value = "save")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> store(@Valid @RequestBody ProductCategoryRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return this.createValidationHttpResponse("Error validation ",result.getFieldErrors());
        }
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Category Product created with success.", OK, productCategoryService.addCategorieProduitRequest(request), true));
    }


    @PutMapping(value = "update/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> update(@Valid @RequestBody ProductCategoryRequest request, @PathVariable Long id, BindingResult result){
        if (result.hasErrors()) {
            return this.createValidationHttpResponse("Error validation ",result.getFieldErrors());
        }
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Category Product updated with success.", OK, productCategoryService.updateCategorieProduitRequest(request, id), true));
    }

    @DeleteMapping(value = "delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> delete(@PathVariable Long id){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Category Product deleted with success.", OK, productCategoryService.deleteCategorieProduitRequest(id), true));
    }

    @GetMapping(value = "all")
   // @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> list() {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Products category list retrieved with success.", OK, productCategoryService.getAllCategorieProduitRequests(), true));
    }

    @GetMapping(value = "{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> getOne(@PathVariable Long id){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Products category retrieved with success.", OK, productCategoryService.getCategorieProduitRequestById(id), true));
    }

    @GetMapping(value = "/search/{name}")
    public ResponseEntity<HttpResponse> removeFromCart(@PathVariable String name){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Product remove from cart with success!", OK, productCategoryService.searchProductCategroy(name), true));
    }


}
