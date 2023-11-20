package be.africshop.africshopbackend.emailModule.controllers;



import be.africshop.africshopbackend.emailModule.dto.MailRequest;
import be.africshop.africshopbackend.emailModule.services.MailService;
import be.africshop.africshopbackend.utils.exceptions.ExceptionHandlers;
import be.africshop.africshopbackend.utils.responses.HttpResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Objects;


import static be.africshop.africshopbackend.utils.JavaUtils.successResponse;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/mail/", produces = APPLICATION_JSON_VALUE)
@PreAuthorize("isAuthenticated()")
@AllArgsConstructor
public class MailController extends ExceptionHandlers {

    private final MailService service;
    @PostMapping(value = "send")
    public ResponseEntity<HttpResponse> index(@Valid @RequestBody MailRequest request, BindingResult result) {

        boolean emailValid = service.emailValidation(request.getMailTo());

        if (result.hasErrors()) {
            return this.createValidationHttpResponse("Error validation ",result.getFieldErrors());
        } else if (!emailValid) {
            return this.createHttpResponse(OK, request.getMailTo()+" is not valid!");
        } else {
            return ResponseEntity.status(OK)
                    .contentType(APPLICATION_JSON)
                    .body(successResponse("Mail sent with success.", OK, service.sendMail(request),true));
        }

    }


}
