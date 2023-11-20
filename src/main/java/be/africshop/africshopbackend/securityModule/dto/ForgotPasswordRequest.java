package be.africshop.africshopbackend.securityModule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordRequest implements Serializable {
    @NotNull
    @NotBlank
    @Size(min = 8)
    private String password;
    @NotNull
    @NotBlank
    @Size(min = 8)
    private String confirmPassword;
    @NotNull
    @NotBlank
    private String username;
}