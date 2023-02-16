package org.leon.todoapp.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.leon.todoapp.jose.Jwks;
import org.leon.todoapp.service.impl.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private RSAKey rsaKey;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    SecurityFilterChain tokenSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/token")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/token").permitAll())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf-> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf( csrf -> csrf.disable() )
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/tokenbybody").permitAll()
                                .requestMatchers("/user").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                                .requestMatchers("/admin").hasAnyAuthority("ROLE_ADMIN")
                                .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt().jwtAuthenticationConverter( new MyCustomAuthenticationConverter()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder,
                                                       ApplicationEventPublisher applicationEventPublisher){
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        ProviderManager providerManager = new ProviderManager(authProvider);
        providerManager.setAuthenticationEventPublisher(authenticationEventPublisher(applicationEventPublisher));
        return providerManager;
    }

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher){
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        rsaKey = Jwks.generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwks) {
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    JwtDecoder jwtDecoder() throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new MyRoleConverter());
        return jwtAuthenticationConverter;
    }

    @Bean
    ApplicationListener<AuthenticationSuccessEvent> successEvent() {
        return event -> {
            System.out.println("Success login " + event.getAuthentication().getClass().getSimpleName() + " " +
                    event.getAuthentication().getName());
        };
    }

    @Bean
    ApplicationListener<AuthenticationFailureBadCredentialsEvent> failureEvent() {
        return event -> {
            System.out.println("Bad credentials " + event.getAuthentication().getClass().getSimpleName() + " " +
                    event.getAuthentication().getName());
        };
    }
}
