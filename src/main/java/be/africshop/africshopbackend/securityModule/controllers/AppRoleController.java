package be.africshop.africshopbackend.securityModule.controllers;


import be.africshop.africshopbackend.securityModule.dto.AppRoleRequest;
import be.africshop.africshopbackend.securityModule.exceptions.AppRoleNotFoundException;
import be.africshop.africshopbackend.securityModule.services.AppRoleService;
import be.africshop.africshopbackend.utils.handlers.HandlerException;
import be.africshop.africshopbackend.utils.responses.HttpResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import static be.africshop.africshopbackend.utils.JavaUtils.API_BASE_URL;
import static be.africshop.africshopbackend.utils.JavaUtils.successResponse;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping(value = API_BASE_URL + "roles", produces = APPLICATION_JSON_VALUE)
public class AppRoleController extends HandlerException {

    private final AppRoleService service;

    @GetMapping("list")
    public ResponseEntity<HttpResponse> index() {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(successResponse("List all roles.", OK, service.listRoles(),true));
    }

    @GetMapping("all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<HttpResponse> list(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(successResponse("List all roles.", OK, service.listPageableRole(page, size),true));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    @PostMapping(value = "/save")
    public ResponseEntity<HttpResponse> store(@Valid @RequestBody AppRoleRequest request, BindingResult result) throws AppRoleNotFoundException {
        if (result.hasErrors()) {
            return this.createValidationHttpResponse("Error de validation", result.getFieldErrors());
        }
        return ResponseEntity.status(CREATED)
                .contentType(APPLICATION_JSON)
                .body(successResponse("Role is created successfully.", CREATED, service.storeRole(request),true));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<HttpResponse> show(@PathVariable Long id) throws AppRoleNotFoundException {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(successResponse("Role Details.", OK, service.showRole(id),true));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    @PutMapping(value = "update/{id}")
    public ResponseEntity<HttpResponse> update(@PathVariable Long id, @Valid @RequestBody AppRoleRequest request, BindingResult result) throws AppRoleNotFoundException {
        if (result.hasErrors()) {
            return this.createValidationHttpResponse("Error de validation", result.getFieldErrors());
        }
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(successResponse("Role is updated successfully.", OK, service.updateRole(id, request),true));
    }
}
