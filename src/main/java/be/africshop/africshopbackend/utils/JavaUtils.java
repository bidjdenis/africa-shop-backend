package be.africshop.africshopbackend.utils;



import be.africshop.africshopbackend.utils.responses.HttpResponse;
import be.africshop.africshopbackend.utils.responses.SuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Component
public class JavaUtils {

    public static final String API_BASE_URL = "/api/v1/";
    public static final String AUTHORITIES = "Authorities";
    public static final String ALPHA_NUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWYZabcdefghijklmnopqrstuvwxyz0123456789,?;.:/=+~%£ùµ*$[]&é\"'(è!çà)-}{|@#^<>";
    public static final String NUMERIC = "0123456789";

    private static final Random RANDOM = new SecureRandom();
    public static final String[] PUBLIC_URLS = {
            API_BASE_URL + "register",
            API_BASE_URL + "register/customer",
            API_BASE_URL + "login",
            API_BASE_URL + "user-details",
            API_BASE_URL + "validation_command/save",
            API_BASE_URL + "forgot/password", "/eureka/**",
            API_BASE_URL + "product/all",
            //API_BASE_URL + "product/upload",
            API_BASE_URL + "product/cart/all",
            API_BASE_URL + "product/wish/all",
            API_BASE_URL + "product/wish/product-id/**",
            API_BASE_URL + "product/featured-categories/all",
            API_BASE_URL + "product/tending/all",
            API_BASE_URL + "product/category/country/**",
            API_BASE_URL + "product/category/**",
            API_BASE_URL + "product/country/**",
            API_BASE_URL + "product/search/**",
       //     API_BASE_URL + "product/*/publish",
            API_BASE_URL + "product/**",
            API_BASE_URL + "product/attachement/**",
            API_BASE_URL + "product/*/add-cart",
            API_BASE_URL + "product/*/quantity/*/update-cart",
            API_BASE_URL + "product/remove-cart/**",
            API_BASE_URL + "product/add-wish/**",
            API_BASE_URL + "product/images/**",
            API_BASE_URL + "unite_mesure/all",
            API_BASE_URL + "unite_mesure/save",
            API_BASE_URL + "unite_mesure/delete/**",
            API_BASE_URL + "unite_mesure/update/**",
            API_BASE_URL + "unite_mesure/search/**",
            API_BASE_URL + "product_category/all",
            API_BASE_URL + "product_category/save",
            API_BASE_URL + "product_category/delete/**",
            API_BASE_URL + "product_category/update/**",
            API_BASE_URL + "product_category/search/**",
            API_BASE_URL + "country/all",
            API_BASE_URL + "country/save",
            API_BASE_URL + "country/delete/**",
            API_BASE_URL + "country/update/**",
            API_BASE_URL + "country/search/**",

    };

    public static <T> HttpResponse successResponse(String message, HttpStatusCode status, T data, boolean success) {
        return Optional.of(data).map(t -> SuccessResponse.builder().data(t).build()).stream()
                .peek(successResponse -> successResponse.setMessage(message))
                .peek(successResponse -> successResponse.setStatus(status))
                .peek(successResponse -> successResponse.setSuccess(success))
                .findFirst().orElseThrow();
    }

    public static String generateRandomString(int length) {
        StringBuilder value = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            value.append(ALPHA_NUMERIC.charAt(RANDOM.nextInt(ALPHA_NUMERIC.length())));
        }
        return new String(value);
    }
}