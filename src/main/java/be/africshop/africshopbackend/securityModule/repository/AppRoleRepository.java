package be.africshop.africshopbackend.securityModule.repository;

import be.africshop.africshopbackend.securityModule.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
    Optional<AppRole> findByName(String name);
}
