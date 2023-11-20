package be.africshop.africshopbackend.commandeModule.services.impls;

import be.africshop.africshopbackend.commandeModule.dto.ValidationCommandRequest;
import be.africshop.africshopbackend.commandeModule.entities.ValidationCommand;
import be.africshop.africshopbackend.commandeModule.repository.ValidationCommandRepository;
import be.africshop.africshopbackend.commandeModule.response.ValidationCommandResponse;
import be.africshop.africshopbackend.commandeModule.services.ValidationCommandService;
import be.africshop.africshopbackend.commandeModule.utils.ValidationCommandConverter;
import be.africshop.africshopbackend.utils.DataStatus;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ValidationCommandImpl implements ValidationCommandService {
    
    private final ValidationCommandRepository repository;
    
    private final ValidationCommandConverter converter;
    
    @Override
    @SneakyThrows
    @Transactional
    public ValidationCommandResponse addValidationCommandRequest(ValidationCommandRequest request) {
        return Optional.of(request).stream()
                .map(converter::validationCommandRequestToObject)
                .peek(requestSet -> {
                    requestSet.setCodeAuto(RandomStringUtils.randomAlphanumeric(10).toUpperCase());})
                .peek(repository::save)
                .map(converter::objectToResponse)
                .findFirst()
                .orElseThrow(() -> new Exception("Error while saving Commande"));
    }

    @Override
    @SneakyThrows
    @Transactional
    public ValidationCommandResponse updateValidationCommandRequest(ValidationCommandRequest request, Long id) {
        ValidationCommand commande = repository.findValidationCommandByDataStatusIsNotAndId(DataStatus.DELETED, id).orElseThrow();
        commande.setDataStatus(DataStatus.UPDATED);
        return Optional.of(request)
                .stream()
                .map(converter::validationCommandRequestToObject)
                .map(repository::save)
                .map(converter::objectToResponse)
                .findFirst()
                .orElseThrow(() -> new Exception("Commande Not found!"));
    }

    @Override
    @SneakyThrows
    @Transactional
    public ValidationCommandResponse deleteValidationCommandRequest(Long id) {

        return repository.findValidationCommandByDataStatusIsNotAndId(DataStatus.DELETED, id)
                .stream()
                .peek(commande -> commande.setDataStatus(DataStatus.DELETED))
                .map(repository::save)
                .map(converter::objectToResponse)
                .findFirst()
                .orElseThrow(() -> new Exception("Commande Not Found!"));
    }

    @Override
    @Transactional(readOnly = true)
    public ValidationCommandResponse getValidationCommandRequestById(Long id) {
        return repository.findValidationCommandByDataStatusIsNotAndId(DataStatus.DELETED, id)
                .map(converter::objectToResponse)
                .orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ValidationCommandResponse> getAllValidationCommandRequests() {
       return repository.getAllValidationCommandsByDataStatusIsNot(DataStatus.DELETED)
                .stream()
                .map(converter::objectToResponse)
                .collect(Collectors.toList());
    }
}
