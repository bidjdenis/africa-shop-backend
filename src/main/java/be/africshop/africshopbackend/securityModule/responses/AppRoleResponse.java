package be.africshop.africshopbackend.securityModule.responses;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AppRoleResponse implements Serializable {
    private Long id;
    private String name;
    private Date creatAt;
    private Date updateAt;
}
