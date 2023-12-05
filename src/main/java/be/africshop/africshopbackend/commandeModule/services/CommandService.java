package be.africshop.africshopbackend.commandeModule.services;



import be.africshop.africshopbackend.commandeModule.dto.CartRequest;
import be.africshop.africshopbackend.commandeModule.dto.CommandRequest;
import be.africshop.africshopbackend.commandeModule.response.CartResponse;
import be.africshop.africshopbackend.commandeModule.response.CommandResponse;


import java.util.List;

public interface CommandService {

    List<CartResponse> orderProduct(Long clientId, List<CartResponse> cartResponses);

    CommandResponse addToShoppingCart(CommandRequest request);

    CommandResponse addCommandeRequest(CommandRequest request);

    CommandResponse updateCommandeRequest(CommandRequest request, Long id);

    CommandResponse deleteCommandeRequest(Long id);

    CommandResponse getCommandeRequestById(Long id);

    List<CommandResponse> getAllCommandeRequests();

}
