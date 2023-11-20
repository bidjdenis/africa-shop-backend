package be.africshop.africshopbackend.securityModule.security;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Slf4j
@Getter
@Setter
public class OldJwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final String token;

    public OldJwtAuthenticationToken(Object principal, Object credentials, String token) {
        super(principal, credentials);
        this.token = token;
    }

    public OldJwtAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, String token) {
        super(principal, credentials, authorities);
        this.token = token;
    }
}
