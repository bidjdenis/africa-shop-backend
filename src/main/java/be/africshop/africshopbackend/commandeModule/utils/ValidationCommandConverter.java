package be.africshop.africshopbackend.commandeModule.utils;

import be.africshop.africshopbackend.commandeModule.dto.ValidationCommandRequest;
import be.africshop.africshopbackend.commandeModule.entities.Country;
import be.africshop.africshopbackend.commandeModule.entities.ValidationCommand;
import be.africshop.africshopbackend.commandeModule.repository.CountryRepository;
import be.africshop.africshopbackend.commandeModule.response.ValidationCommandResponse;
import be.africshop.africshopbackend.utils.DataStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ValidationCommandConverter {

    private final CountryRepository countryRepository;

    public ValidationCommand validationCommandRequestToObject(ValidationCommandRequest request) {
        ValidationCommand validationCommand = new ValidationCommand();
        Country country = countryRepository.findCountryByDataStatusIsNotAndId(DataStatus.DELETED, request.getIdPays()).orElseThrow();
        validationCommand.setCountry(country);
        BeanUtils.copyProperties(request, validationCommand);
        return validationCommand;
    }


    public ValidationCommand validationCommandRequestToObject(ValidationCommandRequest request, ValidationCommand validationCommand) {
        BeanUtils.copyProperties(request, validationCommand);
        return validationCommand;
    }

    public ValidationCommandResponse objectToResponse(ValidationCommand request){
        ValidationCommandResponse response = new ValidationCommandResponse();
        BeanUtils.copyProperties(request, response);
        return response;
    }
    
}
