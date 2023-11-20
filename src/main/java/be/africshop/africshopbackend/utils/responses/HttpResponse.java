package be.africshop.africshopbackend.utils.responses;

import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.io.Serializable;

@Data
public abstract class HttpResponse implements Serializable {
    private HttpStatusCode status;
    private String message;
}
