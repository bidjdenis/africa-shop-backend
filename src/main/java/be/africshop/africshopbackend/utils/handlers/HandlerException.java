package be.africshop.africshopbackend.utils.handlers;

import be.africshop.africshopbackend.utils.exceptions.ExceptionHandlers;
import be.africshop.africshopbackend.utils.exceptions.ObjectNotFoundException;
import be.africshop.africshopbackend.utils.responses.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
public class HandlerException extends ExceptionHandlers {
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<HttpResponse> getObjectNotFoundException(ObjectNotFoundException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }
}
