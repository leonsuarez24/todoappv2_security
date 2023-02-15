package org.leon.todoapp.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MyCustomAuthenticationConverter implements Converter<Jwt, MyCustomAuthenticationToken> {

    @Override
    public MyCustomAuthenticationToken convert(Jwt source) {
        Map<String, String> user = (Map<String, String>) source.getClaims().get("myuser");
        Object authoritiesClaim = user.get("authorities");
        Set<GrantedAuthority> authorities =((Collection<String>) authoritiesClaim).stream()
                .map(authoritie -> new SimpleGrantedAuthority(authoritie))
                .collect(Collectors.toSet());

        MyUser myUser = new MyUser(
                user.get("username"),
                user.get("email"),
                "",
                true,
                true,
                true,
                true,
                authorities
        );
        return new MyCustomAuthenticationToken(authorities, myUser);
    }
}
