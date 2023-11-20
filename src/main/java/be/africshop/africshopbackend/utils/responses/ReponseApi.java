package be.africshop.africshopbackend.utils.responses;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ReponseApi {

    private HttpStatus status;
    private String message;
    private List<?> objects;
    private Object object;
    private boolean success;


    public ReponseApi(HttpStatus status, String message, List<?> objects, boolean success) {
        this.status = status;
        this.message = message;
        this.objects = objects;
        this.success = success;
    }

    public ReponseApi(HttpStatus status, String message, Object object, boolean success) {
        this.status = status;
        this.message = message;
        this.object = object;
        this.success = success;
    }

    public ReponseApi(HttpStatus status, String message, boolean success) {
        this.status = status;
        this.message = message;
        this.success = success;
    }

}
