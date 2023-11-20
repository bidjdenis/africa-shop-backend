package be.africshop.africshopbackend.catalogueModule.services;

import be.africshop.africshopbackend.catalogueModule.dto.UniteMesureRequest;
import be.africshop.africshopbackend.catalogueModule.responses.UniteMesureResponse;

import java.util.List;

public interface UniteMesureService {

    UniteMesureResponse addUniteMeasureRequest(UniteMesureRequest request);

    UniteMesureResponse updateUniteMeasureRequest(UniteMesureRequest request, Long id);

    UniteMesureResponse deleteUniteMeasureRequest(Long id);

    UniteMesureResponse getUniteMeasureRequestById(Long id);

    List<UniteMesureResponse> getAllUniteMeasureRequests();

    List<UniteMesureResponse> searchUnitMeasure(String name);
    
}
