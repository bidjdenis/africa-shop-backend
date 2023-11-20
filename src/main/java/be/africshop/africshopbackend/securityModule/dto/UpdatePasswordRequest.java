package be.africshop.africshopbackend.securityModule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdatePasswordRequest implements Serializable {
    @NotNull
    @NotBlank
    @Size(min = 8)
    private String oldPassword;
    @NotNull
    @NotBlank
    @Size(min = 8)
    private String newPassword;
    @NotNull
    @NotBlank
    @Size(min = 8)
    private String confirmationPassword;
}