package be.africshop.africshopbackend.securityModule.repository;


import be.africshop.africshopbackend.securityModule.entities.AppUser;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    Optional<AppUser> findByEmail(@Email String email);
    Optional<AppUser> findByUsername(String username);
    boolean existsByEmailAndEmailVerify(@Email String email, boolean emailVerify);
    boolean findByUsernameAndEmailVerify(String username, boolean emailVerify);

}
