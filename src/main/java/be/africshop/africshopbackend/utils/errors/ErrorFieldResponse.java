package be.africshop.africshopbackend.utils.errors;

import lombok.Data;

import java.io.Serializable;

@Data
public class ErrorFieldResponse implements Serializable {

    private String field;
    private String defaultMessage;

}
