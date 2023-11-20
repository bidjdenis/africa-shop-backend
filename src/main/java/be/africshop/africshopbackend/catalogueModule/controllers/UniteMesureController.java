package be.africshop.africshopbackend.catalogueModule.controllers;



import be.africshop.africshopbackend.catalogueModule.dto.UniteMesureRequest;
import be.africshop.africshopbackend.catalogueModule.services.UniteMesureService;
import be.africshop.africshopbackend.utils.JavaUtils;
import be.africshop.africshopbackend.utils.handlers.HandlerException;
import be.africshop.africshopbackend.utils.responses.HttpResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/v1/unite_mesure/")
public class UniteMesureController extends HandlerException {

    private final UniteMesureService uniteMesureService;

    public UniteMesureController(UniteMesureService uniteMesureService) {
        this.uniteMesureService = uniteMesureService;
    }


    @PostMapping(value = "save")
    public ResponseEntity<HttpResponse> store(@Valid @RequestBody UniteMesureRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return this.createValidationHttpResponse("Error validation ",result.getFieldErrors());
        }
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Unite Mesure created with success.", OK, uniteMesureService.addUniteMeasureRequest(request), true));
    }


    @PutMapping(value = "update/{id}")
    public ResponseEntity<HttpResponse> update(@Valid @RequestBody UniteMesureRequest request, @PathVariable Long id, BindingResult result){
        if (result.hasErrors()) {
            return this.createValidationHttpResponse("Error validation ",result.getFieldErrors());
        }
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Unite Mesure updated with success.", OK, uniteMesureService.updateUniteMeasureRequest(request, id), true));
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<HttpResponse> delete(@PathVariable Long id){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Unite Mesure deleted with success.", OK, uniteMesureService.deleteUniteMeasureRequest(id), true));
    }

    @GetMapping(value = "all")
    public ResponseEntity<HttpResponse> list() {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Unite Mesure list retrieved with success.", OK, uniteMesureService.getAllUniteMeasureRequests(), true));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<HttpResponse> getOne(@PathVariable Long id){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Unite Mesure retrieved with success.", OK, uniteMesureService.getUniteMeasureRequestById(id), true));
    }

    @GetMapping(value = "search/{name}")
    public ResponseEntity<HttpResponse> search(@PathVariable String name){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Unite Mesure retrieved with success.", OK, uniteMesureService.searchUnitMeasure(name), true));
    }



}
