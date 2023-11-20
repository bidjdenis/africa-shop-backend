package be.africshop.africshopbackend.utils.responses;

import lombok.Data;

import java.io.Serializable;

@Data
public class ValidationFieldResponse implements Serializable {
    private String field;
    private String defaultMessage;
}
