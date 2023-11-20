package be.africshop.africshopbackend.commandeModule.controllers;


import be.africshop.africshopbackend.commandeModule.dto.ValidationCommandRequest;
import be.africshop.africshopbackend.commandeModule.services.ValidationCommandService;
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
@RequestMapping(value = "/api/v1/validation_command/")
public class ValidationCommandController extends HandlerException {

    private final ValidationCommandService service;

    public ValidationCommandController(ValidationCommandService service) {
        this.service = service;
    }
    @PostMapping(value = "save")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> store(@Valid @RequestBody ValidationCommandRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return this.createValidationHttpResponse("Error validation ",result.getFieldErrors());
        }
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("ValidationCommand created with success.", OK, service.addValidationCommandRequest(request), true));
    }


    @PutMapping(value = "update/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> update(@Valid @RequestBody ValidationCommandRequest request, @PathVariable Long id, BindingResult result){
        if (result.hasErrors()) {
            return this.createValidationHttpResponse("Error validation ",result.getFieldErrors());
        }
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("ValidationCommand updated with success.", OK, service.updateValidationCommandRequest(request, id), true));
    }

    @DeleteMapping(value = "delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> delete(@PathVariable Long id){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("ValidationCommand deleted with success.", OK, service.deleteValidationCommandRequest(id), true));
    }

    @GetMapping(value = "all")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> list() {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("ValidationCommand list retrieved with success.", OK, service.getAllValidationCommandRequests(), true));
    }

    @GetMapping(value = "{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> getOne(@PathVariable Long id){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("ValidationCommand retrieved with success.", OK, service.getValidationCommandRequestById(id), true));
    }
    
}
