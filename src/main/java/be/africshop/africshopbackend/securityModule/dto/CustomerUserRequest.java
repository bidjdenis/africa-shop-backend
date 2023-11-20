package be.africshop.africshopbackend.securityModule.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CustomerUserRequest implements Serializable {
    @NotBlank
    @Size(min = 3, max = 50)
    private String firstname;
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;
    @NotBlank
    @Size(min = 20, max = 30)
    private String phone;
    @NotNull(message = "username should not null")
    @Size(min = 3, max = 60)
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Email(message = "Insert valid mail")
    @Size(min = 8, max = 60)
    @NotBlank
    private String email;
    private boolean enabled;
}
