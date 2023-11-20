package be.africshop.africshopbackend.catalogueModule.utils;

import be.africshop.africshopbackend.catalogueModule.dto.UniteMesureRequest;
import be.africshop.africshopbackend.catalogueModule.entities.UniteMeasure;
import be.africshop.africshopbackend.catalogueModule.responses.UniteMesureResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UniteMesureConverter {

    public UniteMeasure uniteMesureRequestToObject(UniteMesureRequest request) {
        UniteMeasure uniteMeasure = new UniteMeasure();
        BeanUtils.copyProperties(request, uniteMeasure);
        return uniteMeasure;
    }


    public UniteMeasure uniteMesureRequestToObject(UniteMesureRequest request, UniteMeasure uniteMeasure) {
        BeanUtils.copyProperties(request, uniteMeasure);
        return uniteMeasure;
    }

    public UniteMesureResponse objectToResponse(UniteMeasure request){
        UniteMesureResponse response = new UniteMesureResponse();
        BeanUtils.copyProperties(request, response);
        return response;
    }
    
}
