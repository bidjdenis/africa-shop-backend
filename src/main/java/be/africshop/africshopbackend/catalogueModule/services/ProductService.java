package be.africshop.africshopbackend.catalogueModule.services;

import be.africshop.africshopbackend.catalogueModule.dto.ProductRequest;
import be.africshop.africshopbackend.catalogueModule.entities.WishList;
import be.africshop.africshopbackend.catalogueModule.responses.ProductFileResponse;
import be.africshop.africshopbackend.catalogueModule.responses.ProductResponse;
import be.africshop.africshopbackend.commandeModule.dto.CartRequest;
import be.africshop.africshopbackend.commandeModule.response.CartResponse;

import java.util.List;

public interface ProductService {

    ProductResponse addProductRequest(ProductRequest request);

    ProductResponse updateProductRequest(ProductRequest request, Long id);

    ProductResponse deleteProductRequest(Long id);

    ProductFileResponse getProductRequestById(Long id);

    List<ProductFileResponse> getAllProductRequests();
    List<ProductFileResponse> getAllProductNotPublush();
    List<ProductFileResponse> getAllProductRequestsFeaturedCategories();
    List<ProductFileResponse> getAllProductRequestsTrending();

    List<ProductFileResponse> getProductsByCategory(Long idProductCategory);
    List<ProductFileResponse> getProductsByCategoryAndCountry(Long idProductCategory, Long idCountry);
    List<ProductFileResponse> getProductsByCountry(Long idCountry);

    List<ProductResponse> getProductsByUniteMeasure(Long idUniteMeasure);

    List<ProductResponse> getProductsByCategoryAndUniteMeasure(Long idProductCategory, Long idUniteMeasure);

    ProductResponse publishProduct(Long id);

    ProductResponse addToCart(Long idProduct, CartRequest request);

    List<CartResponse> getAllCart();

    ProductResponse removeFromCart(Long id);

    List<ProductFileResponse> getAllProductInCart();
    List<ProductFileResponse> searchProduct(String name);

    WishList addProductWhish(Long id);

    List<WishList> getAllWish();

    List<WishList> getAllWishByProduct(Long id);

    CartResponse deleteProductFromCart(Long idProduct);

    CartResponse updateProductCart(Long id, double quantity);


}
