package be.africshop.africshopbackend.commandeModule.services;

import be.africshop.africshopbackend.commandeModule.dto.ValidationCommandRequest;
import be.africshop.africshopbackend.commandeModule.response.ValidationCommandResponse;

import java.util.List;

public interface ValidationCommandService {

    ValidationCommandResponse addValidationCommandRequest(ValidationCommandRequest request);

    ValidationCommandResponse updateValidationCommandRequest(ValidationCommandRequest request, Long id);

    ValidationCommandResponse deleteValidationCommandRequest(Long id);

    ValidationCommandResponse getValidationCommandRequestById(Long id);

    List<ValidationCommandResponse> getAllValidationCommandRequests();


    
}
