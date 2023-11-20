package be.africshop.africshopbackend.commandeModule.utils;

import be.africshop.africshopbackend.commandeModule.dto.CountryRequest;
import be.africshop.africshopbackend.commandeModule.entities.Country;
import be.africshop.africshopbackend.commandeModule.response.CountryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CountryConverter {

    public Country countryRequestToObject(CountryRequest request) {
        Country Country = new Country();
        BeanUtils.copyProperties(request, Country);
        return Country;
    }


    public Country countryRequestToObject(CountryRequest request, Country Country) {
        BeanUtils.copyProperties(request, Country);
        return Country;
    }

    public CountryResponse objectToResponse(Country request){
        CountryResponse response = new CountryResponse();
        BeanUtils.copyProperties(request, response);
        return response;
    }
    
}
