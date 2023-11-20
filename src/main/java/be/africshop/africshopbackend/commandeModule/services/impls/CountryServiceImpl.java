package be.africshop.africshopbackend.commandeModule.services.impls;

import be.africshop.africshopbackend.commandeModule.dto.CountryRequest;
import be.africshop.africshopbackend.commandeModule.entities.Country;
import be.africshop.africshopbackend.commandeModule.repository.CountryRepository;
import be.africshop.africshopbackend.commandeModule.response.CountryResponse;
import be.africshop.africshopbackend.commandeModule.services.CountryService;
import be.africshop.africshopbackend.commandeModule.utils.CountryConverter;
import be.africshop.africshopbackend.utils.DataStatus;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository repository;

    private final CountryConverter converter;

    @Override
    @SneakyThrows
    @Transactional
    public CountryResponse addCountryRequest(CountryRequest request) {
        return Optional.of(request).stream()
                .map(converter::countryRequestToObject)
                .peek(requestSet -> {requestSet.setCodeAuto(RandomStringUtils.randomAlphanumeric(10).toUpperCase());})
                .peek(repository::save)
                .map(converter::objectToResponse)
                .findFirst()
                .orElseThrow(() -> new Exception("Error while saving Country"));
    }

    @Override
    @SneakyThrows
    @Transactional
    public CountryResponse updateCountryRequest(CountryRequest request, Long id) {
        Country country = repository.findCountryByDataStatusIsNotAndId(DataStatus.DELETED, id).orElseThrow();
        country.setDataStatus(DataStatus.UPDATED);
        return Optional.of(request)
                .stream()
                .map(converter::countryRequestToObject)
                .map(repository::save)
                .map(converter::objectToResponse)
                .findFirst()
                .orElseThrow(() -> new Exception("Country Not found!"));
    }

    @Override
    @SneakyThrows
    @Transactional
    public CountryResponse deleteCountryRequest(Long id) {
        return repository.findCountryByDataStatusIsNotAndId(DataStatus.DELETED, id)
                .stream()
                .peek(commande -> commande.setDataStatus(DataStatus.DELETED))
                .map(repository::save)
                .map(converter::objectToResponse)
                .findFirst()
                .orElseThrow(() -> new Exception("Country Not Found!"));
    }

    @Override
    @Transactional(readOnly = true)
    public CountryResponse getCountryRequestById(Long id) {
        return repository.findCountryByDataStatusIsNotAndId(DataStatus.DELETED, id)
                .map(converter::objectToResponse)
                .orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CountryResponse> getAllCountryRequests() {
        return repository.getAllCountryByDataStatusIsNot(DataStatus.DELETED)
                .stream()
                .map(converter::objectToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CountryResponse> searchCountry(String name) {
        List<CountryResponse> countryResponses = new ArrayList<>();
        List<Country> countryList = repository.findByDataStatusIsNotAndLibelleIgnoreCase(DataStatus.DELETED, name);

        for (Country country: countryList) {
            CountryResponse response = new CountryResponse();
            BeanUtils.copyProperties(country, response);
            countryResponses.add(response);
        }
        return countryResponses;
    }
}
