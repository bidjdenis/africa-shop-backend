package be.africshop.africshopbackend.securityModule.services;



import be.africshop.africshopbackend.securityModule.dto.*;
import be.africshop.africshopbackend.securityModule.entities.AppUser;
import be.africshop.africshopbackend.securityModule.responses.AppUserResponse;
import be.africshop.africshopbackend.utils.responses.JwtResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;

public interface AppUserService extends UserDetailsService {

    JwtResponse refreshToken(String token, JwtAuthenticationProvider jwtAuthProvider);

    JwtResponse logUser(LoginRequest request, DaoAuthenticationProvider authenticationProvider);

    AppUserResponse authUser(Authentication authentication);

    AppUserResponse registerUser(UserRequest request);

    AppUserResponse updatePassword(UpdatePasswordRequest request, Authentication authentication);

    AppUser registerUser(CustomerUserRequest request);

   AppUserResponse registerUserCustomer(CustomerUserRequest request);

    AppUserResponse updateUserRoles(UpdateUserRoleRequest request);

    AppUserResponse forgotPassword(ForgotPasswordRequest request);

    Page<AppUserResponse> listUser(int page, int size, String token);

    String validateToken(String token);

    boolean checkUsername(String username);

    boolean checkEmail(String email);

    boolean emailValidation(String email);

    AppUserResponse getUserDetails(Authentication authentication);
}
