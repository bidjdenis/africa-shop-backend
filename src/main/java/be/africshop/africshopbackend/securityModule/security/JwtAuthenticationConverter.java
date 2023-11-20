package be.africshop.africshopbackend.securityModule.security;

import be.africshop.africshopbackend.securityModule.exceptions.UserNotFoundException;
import be.africshop.africshopbackend.securityModule.repository.AppUserRepository;
import be.africshop.africshopbackend.securityModule.utils.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;

@Configuration
@AllArgsConstructor
public class JwtAuthenticationConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {
    private final AppUserRepository repository;

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt source) {
        return repository.findByUsername(source.getSubject())
                .map(UserPrincipal::new)
                .map(userPrincipal -> new UsernamePasswordAuthenticationToken(userPrincipal, source, userPrincipal.getAuthorities()))
                .orElseThrow(() -> new UserNotFoundException("User doesn't exist."));
    }
}
