package be.africshop.africshopbackend.catalogueModule.services.impls;

import be.africshop.africshopbackend.catalogueModule.dto.UniteMesureRequest;
import be.africshop.africshopbackend.catalogueModule.entities.UniteMeasure;
import be.africshop.africshopbackend.catalogueModule.repository.UniteMesureRepository;
import be.africshop.africshopbackend.catalogueModule.responses.UniteMesureResponse;
import be.africshop.africshopbackend.catalogueModule.services.UniteMesureService;
import be.africshop.africshopbackend.catalogueModule.utils.UniteMesureConverter;
import be.africshop.africshopbackend.utils.DataStatus;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UniteMesureServiceImpl implements UniteMesureService {

    private final UniteMesureRepository repository;

    private final UniteMesureConverter converter;

    @Override
    @SneakyThrows
    @Transactional
    public UniteMesureResponse addUniteMeasureRequest(UniteMesureRequest request) {
        return Optional.of(request).stream()
                .map(converter::uniteMesureRequestToObject)
                .peek(uniteMesure -> uniteMesure.setCodeAuto(RandomStringUtils.randomAlphanumeric(10).toUpperCase()))
                .peek(repository::save)
                .map(converter::objectToResponse)
                .findFirst()
                .orElseThrow(() -> new Exception("Error while saving Unite Mesure"));
    }

    @Override
    @SneakyThrows
    @Transactional
    public UniteMesureResponse updateUniteMeasureRequest(UniteMesureRequest request, Long id) {
        UniteMeasure uniteMeasure = repository.findUniteMesureByDataStatusIsNotAndId(DataStatus.DELETED, id).orElseThrow();
        uniteMeasure.setDataStatus(DataStatus.UPDATED);
        return Optional.of(request)
                .map(requestCategery -> converter.uniteMesureRequestToObject(request, uniteMeasure))
                .map(repository::save)
                .map(converter::objectToResponse)
                .orElseThrow(() -> new Exception("Unite Mesure not found"));
    }

    @Override
    @SneakyThrows
    @Transactional
    public UniteMesureResponse deleteUniteMeasureRequest(Long id) {
       return repository.findUniteMesureByDataStatusIsNotAndId(DataStatus.DELETED, id).stream()
                .peek(uniteMesure -> uniteMesure.setDataStatus(DataStatus.DELETED))
                .map(repository::save)
                .findFirst()
                .map(converter::objectToResponse)
                .orElseThrow(()-> new Exception("Unite Mesure not found!"));
    }

    @Override
    @SneakyThrows
    @Transactional(readOnly = true)
    public UniteMesureResponse getUniteMeasureRequestById(Long id) {
        return repository.findUniteMesureByDataStatusIsNotAndId(DataStatus.DELETED, id)
                .map(converter::objectToResponse)
                .orElseThrow(() -> new Exception("Unite Mesure not found!"));
    }

    @Override
    @SneakyThrows
    @Transactional(readOnly = true)
    public List<UniteMesureResponse> getAllUniteMeasureRequests() {
        return repository.getAllUniteMesuresByDataStatusIsNot(DataStatus.DELETED).stream()
                .map(converter::objectToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UniteMesureResponse> searchUnitMeasure(String name) {
        List<UniteMesureResponse> measureArrayList = new ArrayList<>();
        List<UniteMeasure> measureList = repository.findByDataStatusIsNotAndLibelleIgnoreCase(DataStatus.DELETED, name);

        for (UniteMeasure u: measureList) {
            UniteMesureResponse response = new UniteMesureResponse();
            response.setId(u.getId());
            response.setCodeAuto(u.getCodeAuto());
            response.setLibelle(u.getLibelle());
            measureArrayList.add(response);
        }
        return measureArrayList;
    }
}
