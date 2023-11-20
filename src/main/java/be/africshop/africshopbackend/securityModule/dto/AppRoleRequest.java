package be.africshop.africshopbackend.securityModule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class AppRoleRequest implements Serializable {
    @NotNull(message = "Field value is null")
    @NotBlank(message = "Field must not empty")
    private  String name;
}
