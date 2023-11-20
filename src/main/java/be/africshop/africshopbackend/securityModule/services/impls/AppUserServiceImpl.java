package be.africshop.africshopbackend.securityModule.services.impls;

import be.africshop.africshopbackend.emailModule.dto.MailRequest;
import be.africshop.africshopbackend.emailModule.utils.EmailUtils;
import be.africshop.africshopbackend.securityModule.dto.*;
import be.africshop.africshopbackend.securityModule.entities.AppRole;
import be.africshop.africshopbackend.securityModule.entities.AppUser;
import be.africshop.africshopbackend.securityModule.utils.UserPrincipal;
import be.africshop.africshopbackend.securityModule.exceptions.UserNotFoundException;
import be.africshop.africshopbackend.securityModule.repository.AppRoleRepository;
import be.africshop.africshopbackend.securityModule.repository.AppUserRepository;
import be.africshop.africshopbackend.securityModule.responses.AppUserResponse;
import be.africshop.africshopbackend.securityModule.services.AppUserService;
import be.africshop.africshopbackend.securityModule.utils.JavaConvert;
import be.africshop.africshopbackend.securityModule.utils.JwtUtils;
import be.africshop.africshopbackend.utils.responses.JwtResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static be.africshop.africshopbackend.utils.JavaUtils.generateRandomString;


@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class AppUserServiceImpl implements AppUserService {

    private final PasswordEncoder encoder;

    private final AppUserRepository repository;
    private final AppRoleRepository roleRepository;
    private JwtUtils utils;
    private JavaConvert convert;
    private EmailUtils emailUtils;

    @Override
    public JwtResponse refreshToken(String token, JwtAuthenticationProvider jwtAuthProvider) {
        Authentication authentication = jwtAuthProvider.authenticate(new BearerTokenAuthenticationToken(token));
        return utils.getJwtToken(authentication);
    }

    @Override
    public JwtResponse logUser(LoginRequest request, DaoAuthenticationProvider authenticationProvider) {
        Authentication authentication = authenticationProvider.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(request.getUsername(), request.getPassword()));
        return utils.getJwtToken(authentication);
    }

    @Override
    public AppUserResponse authUser(Authentication authentication) {
        return repository.findByUsername(authentication.getName()).map(convert::userToUserResponse).orElseThrow();
    }

    @Override
    public AppUserResponse registerUser(UserRequest request) {
        boolean isExistUser = repository.existsByEmailAndEmailVerify(request.getEmail(), true);
        if (!isExistUser) {
            return Optional.of(request)
                    .map(user -> convert.userRequestToUser(request))
                    .stream()
                    .peek(this::setListRoles)
                    .peek(user -> user.setPassword(encoder.encode(user.getPassword())))
                    .map(repository::save)
                    .findFirst()
                    .map(convert::userToUserResponse)
//                    .map(user -> authenticationProvider.authenticate(UsernamePasswordAuthenticationToken
//                            .unauthenticated(user.getUsername(), user.getUsername())))
//                    .map(utils::getJwtToken)
                    .orElseThrow(() -> new UserNotFoundException("Account already exist"));
        } else throw new UserNotFoundException("Email already exist");
    }

    @Override
    public AppUserResponse updatePassword(UpdatePasswordRequest request, Authentication authentication) {
        return repository.findByUsername(authentication.getName()).stream().peek(user -> {
            if (!encoder.matches(request.getOldPassword(), user.getPassword())) {
                throw new UserNotFoundException("Your old password not match for existing password");
            }
            if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
                throw new UserNotFoundException("Your confirmation password not match your new password");
            }
            user.setPassword(encoder.encode(request.getNewPassword()));
        }).map(repository::save)
                .findFirst()
                .map(convert::userToUserResponse).orElseThrow();
    }


    @Override
    public AppUser registerUser(CustomerUserRequest request) {
        return Optional.of(request)
                .map(user -> convert.userRequestToUser(request))
                .stream().peek(user -> setRoles(user, "ROLE_USER"))
                .peek(user -> {
                    String password = generateRandomString(10);
//                    messageService.sendMail(user.getEmail(), "Votre mot de passe", String.format("Votre compte à été créer. votre mot de passe est: %s", password));
                    log.info("Password : {}", password);
                    user.setPassword(encoder.encode(request.getPassword()));
                }).map(repository::save)
                .findFirst()
                .orElseThrow();
    }

    @Override
    public AppUserResponse registerUserCustomer(CustomerUserRequest request) {

        return Optional.of(request)
                .map(user -> convert.userRequestToUser(request))
                .stream()
                .peek(user -> {
                    user.setPassword(encoder.encode(request.getPassword()));
                })
                .peek(user -> setRoles(user,"ROLE_USER"))
                .map(repository::save)
                .peek(this::sendMail)
                .findFirst()
                .map(convert::userToUserResponse)
                .orElseThrow();
    }

    @Override
    public AppUserResponse updateUserRoles(UpdateUserRoleRequest request) {
        return Optional.of(request).stream()
                .map(user -> repository.findByUsername(request.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found")))
                .peek(user -> user.setAppRoles(request.getAppRoleList()))
                .map(repository::save)
                .map(convert::userToUserResponse).findFirst().orElseThrow();
    }

    @Override
    public AppUserResponse forgotPassword(ForgotPasswordRequest request) {
        return repository.findByUsername(request.getUsername()).stream().peek(user -> {
            if (!request.getPassword().equals(request.getConfirmPassword())) {
                throw new RuntimeException("password not match");
            }
            user.setPassword(encoder.encode(request.getPassword()));
        }).map(repository::save).findFirst().map(convert::userToUserResponse).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .map(UserPrincipal::new).orElseThrow(() -> new UserNotFoundException("User doesn't exist !"));
    }

    @Override
    public Page<AppUserResponse> listUser(int page, int size, String token) {
        Pageable pageable = PageRequest.of(page, size);
        utils.decodeToken(token);
        log.info("Token logged : {}",utils.decodeToken(token));
        return repository.findAll(pageable).map(convert::userToUserResponse);
    }

    @SneakyThrows
    private void setRoles(AppUser user, String roleName) {
        user.getAppRoles().clear();
        AppRole role = roleRepository.findByName(roleName).orElseThrow(() -> new RoleNotFoundException("role doesn't exist !"));
        user.getAppRoles().add(role);
    }

    @SneakyThrows
    private void setListRoles(AppUser user) {
        user.getAppRoles().clear();
        roleRepository.findAll().stream()
                .peek(appRole -> roleRepository.findByName(appRole.getName()).orElseThrow())
                .forEachOrdered(appRole -> user.getAppRoles().add(appRole));
    }

    @Override
    public String validateToken(String token) {
        utils.validateToken(token);
        return "Valide token";
    }

    @Override
    public boolean checkUsername(String username) {
        return repository.findByUsername(username).isPresent();
    }

    @Override
    public boolean checkEmail(String email) {
        return repository.findByEmail(email).isPresent();
    }

    @Override
    public boolean emailValidation(String email) {
        // regex pour valider une adresse e-mail
        String regex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    @Override
    public AppUserResponse getUserDetails(Authentication authentication) {
        AppUser user = repository.findByUsername(authentication.getName()).orElseThrow();
        AppUserResponse response = new AppUserResponse();
        BeanUtils.copyProperties(user, response);
        return response;
    }

    private void sendMail(AppUser appUser) {
        MailRequest request = new MailRequest();

        request.setMailTo(appUser.getEmail());
        request.setName(appUser.getName());
        request.setSubject("création de compte");
        request.setText("L'équipe d'Africa Shop vous présente ses sincères salutation  et vous remercie pour la création de votre compte!");
        emailUtils.sendAccountCreateMail(request);

    }

}
