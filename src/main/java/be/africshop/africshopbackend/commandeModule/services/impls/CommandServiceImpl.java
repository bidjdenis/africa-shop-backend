package be.africshop.africshopbackend.commandeModule.services.impls;

import be.africshop.africshopbackend.catalogueModule.entities.Product;
import be.africshop.africshopbackend.catalogueModule.repository.ProductRepository;
import be.africshop.africshopbackend.commandeModule.dto.CommandRequest;
import be.africshop.africshopbackend.commandeModule.entities.Command;
import be.africshop.africshopbackend.commandeModule.repository.CommandRepository;
import be.africshop.africshopbackend.commandeModule.response.CommandResponse;
import be.africshop.africshopbackend.commandeModule.services.CommandService;
import be.africshop.africshopbackend.commandeModule.utils.CommandConverter;
import be.africshop.africshopbackend.utils.DataStatus;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class CommandServiceImpl implements CommandService {

    private final CommandRepository repository;

    private final CommandConverter converter;

    private final ProductRepository productRepository;


    @Override
    @SneakyThrows
    @Transactional
    public CommandResponse addToShoppingCart(CommandRequest request) {
        Product product = productRepository.findProductByDataStatusIsNotAndId(DataStatus.DELETED, request.getIdProduit()).orElseThrow();
        return Optional.of(request).stream()
                .map(converter::CommandeRequestToObject)
                .peek(requestSet -> {
                    requestSet.setCodeAuto(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
                    requestSet.setProduct(product);
                    requestSet.setShoppingCart(true);
                    requestSet.setQuantity(request.getQuantity());
                    requestSet.setTotalPrice(request.getQuantity() * product.getPrice());
                })
                .peek(repository::save)
                .map(converter::objectToResponse)
                .findFirst()
                .orElseThrow(() -> new Exception("Error while saving Commande"));
    }

    @Override
    @SneakyThrows
    @Transactional
    public CommandResponse addCommandeRequest(CommandRequest request) {
        return Optional.of(request).stream()
                .map(converter::CommandeRequestToObject)
                .peek(requestSet -> {
                    requestSet.setCodeAuto(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
                    requestSet.setNumeroCommande("CMD-" + UUID.randomUUID().toString().toUpperCase());
                    requestSet.setShoppingCart(false);
                })
                .peek(repository::save)
                .map(converter::objectToResponse)
                .findFirst()
                .orElseThrow(() -> new Exception("Error while saving Commande"));
    }

    @Override
    @SneakyThrows
    @Transactional
    public CommandResponse updateCommandeRequest(CommandRequest request, Long id) {
        Command command = repository.findCommandByDataStatusIsNotAndId(DataStatus.DELETED, id).orElseThrow();
        command.setDataStatus(DataStatus.UPDATED);
        return Optional.of(request)
                .stream()
                .map(converter::CommandeRequestToObject)
                .map(repository::save)
                .map(converter::objectToResponse)
                .findFirst()
                .orElseThrow(() -> new Exception("Commande Not found!"));
    }

    @Override
    @SneakyThrows
    @Transactional
    public CommandResponse deleteCommandeRequest(Long id) {
        return repository.findCommandByDataStatusIsNotAndId(DataStatus.DELETED, id)
                .stream()
                .peek(command -> command.setDataStatus(DataStatus.DELETED))
                .map(repository::save)
                .map(converter::objectToResponse)
                .findFirst()
                .orElseThrow(() -> new Exception("Commande Not Found!"));
    }

    @Override
    public CommandResponse getCommandeRequestById(Long id) {
        return repository.findCommandByDataStatusIsNotAndId(DataStatus.DELETED, id)
                .map(converter::objectToResponse)
                .orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommandResponse> getAllCommandeRequests() {
        return repository.getAllCommandesByDataStatusIsNot(DataStatus.DELETED)
                .stream()
                .map(converter::objectToResponse)
                .collect(Collectors.toList());
    }

}
