package be.africshop.africshopbackend.securityModule.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest implements Serializable {
    @NotBlank
    @Size(min = 3, max = 50)
    private String firstname;
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;
    @NotBlank
    @Size(min = 20, max = 30)
    private String phone;
    @NotBlank
    @Size(min = 3, max = 60)
    private String username;
    @NaturalId
    @NotBlank
    @Size(max = 80)
    @Email
    private String email;
    @NotBlank
    @Size(min = 6, max = 100)
    private String password;
}
