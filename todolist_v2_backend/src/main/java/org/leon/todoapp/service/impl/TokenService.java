package org.leon.todoapp.service.impl;

import org.leon.todoapp.dto.JwtUserDto;
import org.leon.todoapp.security.MyUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private final JwtEncoder encoder;

    public TokenService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String generateToken(Authentication authentication) {

        Instant now = Instant.now();

        Set<String> authorities = authentication.getAuthorities().stream().map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toSet());

        MyUser myUser = (MyUser) authentication.getPrincipal();
        JwtUserDto jwtUserDto = new JwtUserDto(
                authentication.getName(),
                myUser.getEmail(),
                myUser.getPassword(),
                authorities,
                myUser.isAccountNonExpired(),
                myUser.isAccountNonLocked(),
                myUser.isCredentialsNonExpired(),
                myUser.isEnabled()
                );

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(authentication.getName())
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("authorities", authorities)
                .claim("myuser", jwtUserDto)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
