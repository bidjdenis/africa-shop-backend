package be.africshop.africshopbackend.securityModule.controllers;


import be.africshop.africshopbackend.securityModule.dto.*;
import be.africshop.africshopbackend.securityModule.services.AppUserService;
import be.africshop.africshopbackend.utils.handlers.HandlerException;
import be.africshop.africshopbackend.utils.responses.HttpResponse;
import be.africshop.africshopbackend.utils.responses.ReponseApi;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
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
@CrossOrigin("*")
@RequestMapping(value = API_BASE_URL , produces = APPLICATION_JSON_VALUE)
public class AuthenticationController extends HandlerException {
    private final AppUserService userService;
    private final DaoAuthenticationProvider authenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @PostMapping(value = "refresh/token")
    public ResponseEntity<HttpResponse> refreshToken(@RequestParam("token") String token) {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(successResponse("Token is updated successfully", OK, userService.refreshToken(token, jwtAuthenticationProvider),true));
    }

    @PostMapping(value = "register", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponse> register(@Valid @RequestBody UserRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return this.createValidationHttpResponse("Fields error validation", result.getFieldErrors());
        }
        return ResponseEntity.status(CREATED).contentType(APPLICATION_JSON)
                .body(successResponse("User create successfully!", CREATED, userService.registerUser(request),true));
    }

    @PostMapping(value = "register/customer", consumes = APPLICATION_JSON_VALUE)
    public ReponseApi registerClient(@Valid @RequestBody CustomerUserRequest request, BindingResult result) {
        boolean checkUsername = userService.checkUsername(request.getUsername());
        boolean checkEmail = userService.checkEmail(request.getEmail());
        boolean checkEmailValid = userService.emailValidation(request.getEmail());
        if (result.hasErrors()) {
            return new ReponseApi(HttpStatus.OK, "Fields error validation", result.getFieldErrors(), false);
        } else if (checkUsername){
            return new ReponseApi(HttpStatus.OK, request.getUsername()+" is already exist!", false);

        }else if (!checkEmailValid) {
            return new ReponseApi(HttpStatus.OK, request.getEmail()+" is not valid!", false);

        } else if (checkEmail) {
            return new ReponseApi(HttpStatus.OK,  request.getEmail()+" is already exist!", false);

        }
        return new ReponseApi(HttpStatus.OK,  " User member create successfully!", userService.registerUserCustomer(request), true);

    }

    @PostMapping(value = "me")
    public ResponseEntity<HttpResponse> me() {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(successResponse("Logged in User Details", OK, userService.authUser(SecurityContextHolder.getContext().getAuthentication()),true));
    }
    @PostMapping(value = "login", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponse> login(@Valid @RequestBody LoginRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return this.createValidationHttpResponse("Validations error", result.getFieldErrors());
        }
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(successResponse("You are connected", OK, userService.logUser(request, authenticationProvider),true));
    }

    @PostMapping(value = "update/password", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponse> updatePassword(@Valid @RequestBody UpdatePasswordRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return this.createValidationHttpResponse("Validation errors", result.getFieldErrors());
        }
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(successResponse("Password is update successfully", OK, userService.updatePassword(request, SecurityContextHolder.getContext().getAuthentication()),true));
    }
    @GetMapping("users")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<HttpResponse> index(@RequestParam int page, @RequestParam int size, @RequestParam String token) {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(successResponse("List all users", OK, userService.listUser(page, size,token),true));
    }
    @PostMapping(value = "forgot/password", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponse> forgetPassword(@Valid @RequestBody ForgotPasswordRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return this.createValidationHttpResponse("Error de validation", result.getFieldErrors());
        }
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(successResponse("Password update successfully", OK, userService.forgotPassword(request),true));
    }

    @PostMapping(value = "update/roles", consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<HttpResponse> updateUserRoles(@Valid @RequestBody UpdateUserRoleRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return this.createValidationHttpResponse("Error de validation", result.getFieldErrors());
        }
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(successResponse("Role update successfully", OK, userService.updateUserRoles(request),true));
    }

    @PostMapping(value = "validate/token", consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<HttpResponse> validateToken(@RequestBody String token ) {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(successResponse("Token retrieve successfully", OK, userService.validateToken(token),true));
    }

    @GetMapping(value = "user-details")
    public ResponseEntity<HttpResponse> getUser() {

        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(successResponse("Token retrieve successfully", OK, userService.getUserDetails(SecurityContextHolder.getContext().getAuthentication()),true));
    }



}