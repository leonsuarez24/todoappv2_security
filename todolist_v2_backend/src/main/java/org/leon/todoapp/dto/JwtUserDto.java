package org.leon.todoapp.dto;

import java.util.Set;

public record JwtUserDto (
        String username,
        String email,
        String password,
        Set<String> authorities,
        boolean accountNonExpired,
        boolean accountNonLocked,
        boolean credentialsNonExpired,
        boolean enabled
){
}
