package be.africshop.africshopbackend.commandeModule.controllers;

import be.africshop.africshopbackend.commandeModule.dto.CountryRequest;
import be.africshop.africshopbackend.commandeModule.services.CountryService;
import be.africshop.africshopbackend.utils.JavaUtils;
import be.africshop.africshopbackend.utils.handlers.HandlerException;
import be.africshop.africshopbackend.utils.responses.HttpResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/v1/country/")
public class CountryController extends HandlerException {

    private final CountryService service;

    public CountryController(CountryService service) {
        this.service = service;
    }


    @PostMapping(value = "save")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> store(@Valid @RequestBody CountryRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return this.createValidationHttpResponse("Error validation ",result.getFieldErrors());
        }
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Country created with success.", OK, service.addCountryRequest(request), true));
    }


    @PutMapping(value = "update/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> update(@Valid @RequestBody CountryRequest request, @PathVariable Long id, BindingResult result){
        if (result.hasErrors()) {
            return this.createValidationHttpResponse("Error validation ",result.getFieldErrors());
        }
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Country updated with success.", OK, service.updateCountryRequest(request, id), true));
    }

    @DeleteMapping(value = "delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> delete(@PathVariable Long id){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Country deleted with success.", OK, service.deleteCountryRequest(id), true));
    }

    @GetMapping(value = "all")
   // @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> list() {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Country list retrieved with success.", OK, service.getAllCountryRequests(), true));
    }

    @GetMapping(value = "{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> getOne(@PathVariable Long id){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Country retrieved with success.", OK, service.getCountryRequestById(id), true));
    }

    @GetMapping(value = "search/{name}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> search(@PathVariable String name){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Country retrieved with success.", OK, service.searchCountry(name), true));
    }
    
}
