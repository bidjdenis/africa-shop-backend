package be.africshop.africshopbackend.securityModule.responses;


import be.africshop.africshopbackend.securityModule.entities.AppRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserResponse implements Serializable {
    private Long id;
    private String firstname;
    private String name;
    private String phone;
    private String email;
    private String username;
    private boolean enable;
    private boolean emailVerify;
    private boolean locked;
    private boolean neverConnected;
    private boolean estConnecter;
    private String photoProfileUrl;
    private boolean expiredAt;
    private Date expireDate;
    private String photoProfileReference;
    private Date lasTimeConnected;
    private  Collection<AppRole> appRoles = new ArrayList<>();

}
