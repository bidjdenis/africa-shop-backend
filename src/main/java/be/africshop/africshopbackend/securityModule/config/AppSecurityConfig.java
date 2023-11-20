package be.africshop.africshopbackend.securityModule.config;
import be.africshop.africshopbackend.securityModule.security.JwtAuthenticationConverter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

import static be.africshop.africshopbackend.utils.JavaUtils.PUBLIC_URLS;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity
public class AppSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationConverter converter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
       return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorization -> authorization.requestMatchers(PUBLIC_URLS).permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oAuth2 -> oAuth2.jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(converter)))

//               .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(new BearerTokenAccessDeniedHandler()).authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint()))
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService){
        var authPr = new DaoAuthenticationProvider();
        authPr.setPasswordEncoder(passwordEncoder);
        authPr.setUserDetailsService(userDetailsService);
        return new ProviderManager(authPr);
    }
}