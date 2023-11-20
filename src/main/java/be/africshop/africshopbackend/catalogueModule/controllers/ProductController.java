package be.africshop.africshopbackend.catalogueModule.controllers;

import be.africshop.africshopbackend.catalogueModule.dto.ProductRequest;
import be.africshop.africshopbackend.catalogueModule.entities.FileAttachement;
import be.africshop.africshopbackend.catalogueModule.repository.FileAtachementRepository;
import be.africshop.africshopbackend.catalogueModule.responses.ProductResponse;
import be.africshop.africshopbackend.catalogueModule.services.FileAttachementService;
import be.africshop.africshopbackend.catalogueModule.services.ProductService;
import be.africshop.africshopbackend.commandeModule.dto.CartRequest;
import be.africshop.africshopbackend.utils.FileUpload;
import be.africshop.africshopbackend.utils.JavaUtils;
import be.africshop.africshopbackend.utils.handlers.HandlerException;
import be.africshop.africshopbackend.utils.responses.HttpResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/v1/product/")
public class ProductController extends HandlerException {
    
    private final ProductService productService;

    private final FileAtachementRepository atachementRepository;

    private final FileAttachementService attachementService;

    private final FileUpload fileUpload;

    @Value("${upload.directory}")
    private String uploadDir;


    public ProductController(ProductService productService, FileAtachementRepository atachementRepository, FileAttachementService attachementService, FileUpload fileUpload) {
        this.productService = productService;
        this.atachementRepository = atachementRepository;
        this.attachementService = attachementService;
        this.fileUpload = fileUpload;
    }

    @PostMapping(value = "save")
    public ResponseEntity<HttpResponse> store(@Valid @RequestBody ProductRequest request, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return this.createValidationHttpResponse("Error validation ",result.getFieldErrors());
        }

        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse(request.getLibelle()+" created with success!", OK, productService.addProductRequest(request), true));
    }

    @PutMapping(value = "update/{id}")
    public ResponseEntity<HttpResponse> update(@Valid @RequestBody ProductRequest request, @PathVariable Long id, BindingResult result) throws IOException {

        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse(request.getLibelle()+" updated with success!", OK, productService.updateProductRequest(request, id), true));
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<HttpResponse> delete(@PathVariable Long id){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Product deleted with success!", OK, productService.deleteProductRequest(id), true));
    }

    @GetMapping(value = "all")
    public ResponseEntity<HttpResponse> list() {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Products list retrieved with success!", OK, productService.getAllProductRequests(), true));
    }

    @GetMapping(value = "not-publish/all")
    public ResponseEntity<HttpResponse> getAllNotPublish() {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Products list retrieved with success!", OK, productService.getAllProductNotPublush(), true));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<HttpResponse> getOne(@PathVariable Long id){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Product retrieved with success!", OK, productService.getProductRequestById(id), true));
    }

    @GetMapping(value = "/category/{id}")
    public ResponseEntity<HttpResponse> getAllByCategory(@PathVariable Long id){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Products by Category retrieved with success!", OK, productService.getProductsByCategory(id), true));
    }

   @GetMapping(value = "/category/{idCategory}/country/{idCountry}")
    public ResponseEntity<HttpResponse> getAllByCategoryAndCountry(@PathVariable Long idCategory, @PathVariable Long idCountry){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Products by Category retrieved with success!", OK, productService.getProductsByCategoryAndCountry(idCategory, idCountry), true));
    }
   @GetMapping(value = "/country/{idCountry}")
    public ResponseEntity<HttpResponse> getAllByCountry(@PathVariable Long idCountry){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Products by Category retrieved with success!", OK, productService.getProductsByCountry(idCountry), true));
    }

    @GetMapping(value = "/unite_measure/{id}")
    public ResponseEntity<HttpResponse> getAllByUniteMeasure(@PathVariable Long id){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Products by Unite Measure retrieved with success!", OK, productService.getProductsByUniteMeasure(id), true));
    }


    @GetMapping(value = "/category/{idCategory}/unite_measure/{idUniteMeasure}")
    public ResponseEntity<HttpResponse> getAllByCategoryAndUniteMeasure(@PathVariable Long idCategory, @PathVariable Long idUniteMeasure){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Products by Category and Unite Mesure retrieved with success!", OK, productService.getProductsByCategoryAndUniteMeasure(idCategory, idUniteMeasure), true));
    }

    @GetMapping(value = "{id}/publish")
    public ResponseEntity<HttpResponse> publish(@PathVariable Long id){
        ProductResponse response = productService.publishProduct(id);

        if (!response.getPublish()) {
            return ResponseEntity.status(OK)
                    .contentType(APPLICATION_JSON)
                    .body(JavaUtils.successResponse(response.getLibelle()+" unpublished with success!", OK, response, true));
        }

        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse(response.getLibelle()+" published with success!", OK, response, true));
    }

    @PostMapping("upload/{productId}")
    public ResponseEntity<HttpResponse> upload(@RequestParam("files") MultipartFile[] files, @PathVariable Long productId) throws IOException {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse(" published with success!", OK, fileUpload.uploadFile(files, productId), true));
    }


    @GetMapping("/images/{fileName}")
    public ResponseEntity<Resource> afficherImage(@PathVariable String fileName) {

        String dossier = uploadDir+"/"+fileName;
        Path path = Paths.get(dossier);
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/attachement/{id}")
    public ResponseEntity<HttpResponse> getAllAttachement(@PathVariable Long id){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Product Attachements retrieved with success!", OK, attachementService.getAllByProduct(id), true));
    }

    @PostMapping(value = "/{idProduct}/add-cart")
    public ResponseEntity<HttpResponse> addToCart(@PathVariable Long idProduct, @RequestBody CartRequest request){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Product add to cart with success!", OK, productService.addToCart(idProduct, request), true));
    }

    @GetMapping(value = "/{idProduct}/quantity/{quantity}/update-cart")
    public ResponseEntity<HttpResponse> updateCart(@PathVariable Long idProduct, @PathVariable double quantity){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Product add to cart with success!", OK, productService.updateProductCart(idProduct, quantity), true));
    }
    @GetMapping(value = "/remove-cart/{id}")
    public ResponseEntity<HttpResponse> removeFromCart(@PathVariable Long id){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Product remove from cart with success!", OK, productService.deleteProductFromCart(id), true));
    }

    @GetMapping(value = "/cart/all")
    public ResponseEntity<HttpResponse> listCart() {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Products list retrieved with success!", OK, productService.getAllCart(), true));
    }

    @GetMapping(value = "/add-wish/{id}")
    public ResponseEntity<HttpResponse> addWish(@PathVariable Long id) {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Wish added with success!", OK, productService.addProductWhish(id), true));
    }



    @GetMapping(value = "/wish/all")
    public ResponseEntity<HttpResponse> getAllWish() {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Wish list retrieved with success!", OK, productService.getAllWish(), true));
    }

    @GetMapping(value = "/wish/product-id/{id}")
    public ResponseEntity<HttpResponse> getAllWishByProduct(@PathVariable Long id) {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Wish list retrieved with success!", OK, productService.getAllWishByProduct(id), true));
    }


    @GetMapping(value = "/search/{name}")
    public ResponseEntity<HttpResponse> removeFromCart(@PathVariable String name){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Product remove from cart with success!", OK, productService.searchProduct(name), true));
    }

    @GetMapping(value = "/featured-categories/all")
    public ResponseEntity<HttpResponse> getAllFeaturedCategories() {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Wish list retrieved with success!", OK, productService.getAllProductRequestsFeaturedCategories(), true));
    }

    @GetMapping(value = "/tending/all")
    public ResponseEntity<HttpResponse> getAllTending() {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Wish list retrieved with success!", OK, productService.getAllProductRequestsTrending(), true));
    }


}
