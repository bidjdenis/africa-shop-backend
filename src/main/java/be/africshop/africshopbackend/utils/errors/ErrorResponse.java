package be.africshop.africshopbackend.utils.errors;

import be.africshop.africshopbackend.utils.responses.HttpResponse;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class ErrorResponse extends HttpResponse {
    private Object validations;
    private String reason;
}
