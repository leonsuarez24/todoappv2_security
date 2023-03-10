package org.leon.todoapp.security;

import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class MyRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @SuppressWarnings("unchecked")
    @Override
    public Collection<GrantedAuthority> convert (Jwt source) {
        Map<String, Object> rolesClaims = source.getClaims();
        if (rolesClaims == null || rolesClaims.isEmpty()){
            return new ArrayList<>();
        }
        Collection<String> authorities = (Collection<String>) rolesClaims.get("authorities");
        return authorities.stream().map(authoritie -> new SimpleGrantedAuthority(authoritie))
                .collect(Collectors.toSet());
    }
}
