package be.africshop.africshopbackend.commandeModule.services;

import be.africshop.africshopbackend.commandeModule.dto.CountryRequest;
import be.africshop.africshopbackend.commandeModule.response.CountryResponse;


import java.util.List;

public interface CountryService {

    CountryResponse addCountryRequest(CountryRequest request);

    CountryResponse updateCountryRequest(CountryRequest request, Long id);

    CountryResponse deleteCountryRequest(Long id);

    CountryResponse getCountryRequestById(Long id);

    List<CountryResponse> getAllCountryRequests();

    List<CountryResponse> searchCountry(String name);
}
