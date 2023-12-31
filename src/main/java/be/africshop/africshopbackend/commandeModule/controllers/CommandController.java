package be.africshop.africshopbackend.commandeModule.controllers;

import be.africshop.africshopbackend.commandeModule.dto.CommandRequest;
import be.africshop.africshopbackend.commandeModule.response.CartResponse;
import be.africshop.africshopbackend.commandeModule.services.CommandService;
import be.africshop.africshopbackend.utils.JavaUtils;
import be.africshop.africshopbackend.utils.handlers.HandlerException;
import be.africshop.africshopbackend.utils.responses.HttpResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@RestController
@RequestMapping(value = "/api/v1/command/")
@CrossOrigin("*")
public class CommandController extends HandlerException {

    private final CommandService service;

    public CommandController(CommandService service) {
        this.service = service;
    }


    @PostMapping(value = "save")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> store(@Valid @RequestBody CommandRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return this.createValidationHttpResponse("Error validation ",result.getFieldErrors());
        }
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Command created with success.", OK, service.addCommandeRequest(request), true));
    }


    @PutMapping(value = "update/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> update(@Valid @RequestBody CommandRequest request, @PathVariable Long id, BindingResult result){
        if (result.hasErrors()) {
            return this.createValidationHttpResponse("Error validation ",result.getFieldErrors());
        }
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Command updated with success.", OK, service.updateCommandeRequest(request, id), true));
    }

    @DeleteMapping(value = "delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> delete(@PathVariable Long id){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Command deleted with success.", OK, service.deleteCommandeRequest(id), true));
    }

    @GetMapping(value = "all")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> list() {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("command list retrieved with success.", OK, service.getAllCommandeRequests(), true));
    }

    @GetMapping(value = "{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> getOne(@PathVariable Long id){
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("command retrieved with success.", OK, service.getCommandeRequestById(id), true));
    }

    @PostMapping(value = "order/{clientId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> order(@Valid @RequestBody List<CartResponse> cartResponses, @PathVariable Long clientId, BindingResult result) {
        if (result.hasErrors()) {
            return this.createValidationHttpResponse("Error validation ",result.getFieldErrors());
        }
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(JavaUtils.successResponse("Command created with success.", OK, service.orderProduct(clientId, cartResponses), true));
    }


}
