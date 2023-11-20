package be.africshop.africshopbackend.securityModule.dto;

import be.africshop.africshopbackend.securityModule.entities.AppRole;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UpdateUserRoleRequest implements Serializable {
    private String username;
    private String email;
    private List<AppRole> appRoleList;
}
