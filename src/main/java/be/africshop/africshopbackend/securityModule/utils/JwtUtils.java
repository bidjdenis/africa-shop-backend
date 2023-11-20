package be.africshop.africshopbackend.securityModule.utils;


import be.africshop.africshopbackend.utils.responses.JwtResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static be.africshop.africshopbackend.utils.JavaUtils.AUTHORITIES;


@Slf4j
@Component
public class JwtUtils {

    @Autowired
    private JwtEncoder accessTokenEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    @Qualifier("jwtRefreshTokenEncoder")
    private JwtEncoder refreshTokenEncoder;

    private String createAccessToken(Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        Instant now = Instant.now();
        String[] claims = getClaimsFromUser(user);

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("keyApp")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(user.getUsername())
                .claim(AUTHORITIES, claims)
                .build();
        return accessTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    private String createRefreshToken(Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        Instant now = Instant.now();
        String[] claims = getClaimsFromUser(user);

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("keyApp")
                .issuedAt(now)
                .expiresAt(now.plus(30, ChronoUnit.DAYS))
                .subject(user.getUsername())
                .claim(AUTHORITIES, claims)
                .build();
        return refreshTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
    public Jwt decodeToken(String token) throws JwtException {
        return jwtDecoder.decode(token);
    }

    public void validateToken(String token){
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(AUTHORITIES);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String[] getClaimsFromUser(UserDetails user) {
        return user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);
    }

    public JwtResponse getJwtToken(Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof UserDetails)) {
            throw new BadCredentialsException("Username not found ! ");
        }
        String refreshToken;
        if (authentication.getCredentials() instanceof Jwt) {
            Instant now = Instant.now();
            Instant expireAt = ((Jwt) authentication.getCredentials()).getExpiresAt();
            Duration duration = Duration.between(now, expireAt);
            long dayUntilExpired = duration.toDays();
            if (dayUntilExpired < 7) {
                refreshToken = createRefreshToken(authentication);
            } else {
                refreshToken = ((Jwt) authentication.getCredentials()).getTokenValue();
            }
        } else {
            refreshToken = createRefreshToken(authentication);
        }

        return JwtResponse.builder().access_token(createAccessToken(authentication)).refresh_token(refreshToken).build();
    }

}