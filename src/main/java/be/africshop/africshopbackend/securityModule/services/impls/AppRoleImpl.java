package be.africshop.africshopbackend.securityModule.services.impls;

import be.africshop.africshopbackend.securityModule.dto.AppRoleRequest;
import be.africshop.africshopbackend.securityModule.exceptions.AppRoleNotFoundException;
import be.africshop.africshopbackend.securityModule.repository.AppRoleRepository;
import be.africshop.africshopbackend.securityModule.responses.AppRoleResponse;
import be.africshop.africshopbackend.securityModule.services.AppRoleService;
import be.africshop.africshopbackend.securityModule.utils.JavaConvert;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class AppRoleImpl implements AppRoleService {

    private final JavaConvert convert;
    private final AppRoleRepository repository;

    @Override
    public AppRoleResponse storeRole(AppRoleRequest request) throws AppRoleNotFoundException {
        return Optional.of(request)
                .map(convert::roleRequestToObject)
                .map(repository::save).map(convert::roleObjectToResponse)
                .orElseThrow(() -> new AppRoleNotFoundException("Role not found"));
    }

    @Override
    public AppRoleResponse showRole(Long id) throws AppRoleNotFoundException {
        return repository.findById(id)
                .map(convert::roleObjectToResponse)
                .orElseThrow(() -> new AppRoleNotFoundException("Role not found"));
    }

    @Override
    public AppRoleResponse updateRole(Long id, AppRoleRequest request) throws AppRoleNotFoundException {
        return repository.findById(id)
                .map(role -> convert.roleRequestToObject(request, role))
                .map(repository::save).map(convert::roleObjectToResponse)
                .orElseThrow(() -> new AppRoleNotFoundException("Role not found"));
    }

    @Override
    public List<AppRoleResponse> listRoles() {
        return repository.findAll()
                .stream().
                map(convert::roleObjectToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<AppRoleResponse> listPageableRole(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable).map(convert::roleObjectToResponse);
    }

}
