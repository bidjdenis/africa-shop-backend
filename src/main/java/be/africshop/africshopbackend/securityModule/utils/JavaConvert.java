package be.africshop.africshopbackend.securityModule.utils;


import be.africshop.africshopbackend.securityModule.dto.AppRoleRequest;
import be.africshop.africshopbackend.securityModule.dto.CustomerUserRequest;
import be.africshop.africshopbackend.securityModule.dto.UserRequest;
import be.africshop.africshopbackend.securityModule.entities.AppRole;
import be.africshop.africshopbackend.securityModule.entities.AppUser;
import be.africshop.africshopbackend.securityModule.responses.AppRoleResponse;
import be.africshop.africshopbackend.securityModule.responses.AppUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;


@Slf4j
@Component
public class JavaConvert {

    public AppUser userRequestToUser(UserRequest request) {
        AppUser user = new AppUser();
        Instant now = Instant.now();
        BeanUtils.copyProperties(request, user);
        user.setExpireDate(now.plus(15, ChronoUnit.MINUTES));
        user.setLocked(false);
//        user.setEmailVerify(false);
        return user;
    }

    public AppUser userRequestToUser(CustomerUserRequest request) {
        AppUser user = new AppUser();
        Instant now = Instant.now();
        BeanUtils.copyProperties(request, user);
        user.setExpireDate(now.plus(15, ChronoUnit.MINUTES));
        user.setLocked(false);
        return user;
    }

    public AppUser userRequestToUser(AppUser user, UserRequest request) {
        BeanUtils.copyProperties(request, user);
        return user;
    }

    public AppUserResponse userToUserResponse(AppUser user) {
        AppUserResponse response = new AppUserResponse();
        BeanUtils.copyProperties(user, response);
        return response;
    }

    public AppRole roleRequestToObject(AppRoleRequest request) {
        AppRole role = new AppRole();
        BeanUtils.copyProperties(request, role);
        return role;
    }

    public AppRoleResponse roleObjectToResponse(AppRole role) {
        AppRoleResponse response = new AppRoleResponse();
        BeanUtils.copyProperties(role, response);
        return response;
    }

    public AppRole roleResponseToObject(AppRoleResponse response) {
        AppRole role = new AppRole();
        BeanUtils.copyProperties(response, role);
        return role;
    }

    public AppRole roleRequestToObject(AppRoleRequest request, AppRole appRole) {
        BeanUtils.copyProperties(request, appRole);
        return appRole;
    }


}
