package be.africshop.africshopbackend.utils.exceptions;

import be.africshop.africshopbackend.utils.errors.ErrorFieldResponse;
import be.africshop.africshopbackend.utils.errors.ErrorResponse;
import be.africshop.africshopbackend.utils.responses.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlers implements ErrorController {

    private static final String ACCOUNT_LOCKED = "Vôtre compte a été bloqué. Veuillez contacter l'administration";
    private static final String METHOD_IS_NOT_ALLOWED = "Cette méthode de demande n'est pas autorisée sur ce point de terminaison. Veuillez envoyer une demande %s";
    private static final String INCORRECT_CREDENTIALS = "Nom d'utilisateur / mot de passe incorrect. Veuillez réessayer";
    private static final String ACCOUNT_DISABLED = "Votre compte a été désactivé. S'il s'agit d'une erreur, veuillez contacter l'administration";
    private static final String ERROR_PROCESSING_FILE = "Une erreur s'est produite lors du traitement du fichier";
    private static final String NOT_ENOUGH_PERMISSION = "Vous n'avez pas assez d'autorisation";


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpResponse> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        System.out.println(exception.getAllErrors());
        return createValidationHttpResponse("Validation errors", exception.getFieldErrors());
    }


    @ExceptionHandler(IOException.class)
    public ResponseEntity<HttpResponse> ioException(IOException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
        log.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(RuntimeException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    protected ResponseEntity<HttpResponse> createHttpResponse(HttpStatus status, String message) {
        HttpResponse response = ErrorResponse.builder().reason(status.getReasonPhrase()).build();
        response.setStatus(status);
        response.setMessage(message);
        return new ResponseEntity<>(response, status);
    }

    protected ResponseEntity<HttpResponse> createValidationHttpResponse(String message, List<FieldError> errors) {
        List<ErrorFieldResponse> fieldResponses = errors.stream().map(fieldError -> {
            ErrorFieldResponse response = new ErrorFieldResponse();
            BeanUtils.copyProperties(fieldError, response);
            return response;
        }).toList();
        HttpResponse response = ErrorResponse.builder().reason(HttpStatus.BAD_REQUEST.getReasonPhrase()).validations(fieldResponses).build();
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setMessage(message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



}
