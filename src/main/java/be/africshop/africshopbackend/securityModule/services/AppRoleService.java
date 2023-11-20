package be.africshop.africshopbackend.securityModule.services;


import be.africshop.africshopbackend.securityModule.dto.AppRoleRequest;
import be.africshop.africshopbackend.securityModule.exceptions.AppRoleNotFoundException;
import be.africshop.africshopbackend.securityModule.responses.AppRoleResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AppRoleService {
    AppRoleResponse storeRole(AppRoleRequest request) throws AppRoleNotFoundException;

    AppRoleResponse showRole(Long id) throws AppRoleNotFoundException;

    AppRoleResponse updateRole(Long id, AppRoleRequest request) throws AppRoleNotFoundException;

    List<AppRoleResponse> listRoles();

    Page<AppRoleResponse> listPageableRole(int page, int size);
}
