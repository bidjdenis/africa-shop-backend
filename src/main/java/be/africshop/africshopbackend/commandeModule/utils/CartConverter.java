package be.africshop.africshopbackend.commandeModule.utils;


import be.africshop.africshopbackend.commandeModule.dto.CartRequest;
import be.africshop.africshopbackend.commandeModule.entities.Cart;
import be.africshop.africshopbackend.commandeModule.response.CartResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CartConverter {

    public Cart CartRequestToObject(CartRequest request) {
        Cart Cart = new Cart();
        BeanUtils.copyProperties(request, Cart);
        return Cart;
    }


    public Cart CartRequestToObject(CartRequest request, Cart Cart) {
        BeanUtils.copyProperties(request, Cart);
        return Cart;
    }

    public CartResponse objectToResponse(Cart request){
        CartResponse response = new CartResponse();
        BeanUtils.copyProperties(request, response);
        return response;
    }
}
