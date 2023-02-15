package org.leon.todoapp.service.impl;

import org.hibernate.validator.internal.util.logging.Log;
import org.leon.todoapp.entity.Users;
import org.leon.todoapp.repository.UsersRepository;
import org.leon.todoapp.security.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private  UsersRepository usersRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Users> userByUsername = usersRepository.findByUsername(username);

        if (!userByUsername.isPresent()) {
            throw new UsernameNotFoundException("Username not found");
        }

        Users user = userByUsername.get();

        if (user == null || !user.getUsername().equals(username)){
            throw new UsernameNotFoundException("Invalid credentials");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getAuthorities().stream().forEach(authoritie ->
                grantedAuthorities.add(new SimpleGrantedAuthority(authoritie.getAuthority())));

        return new MyUser(user.getUsername(), user.getEmail(), user.getPassword(),
                true, true, true, true, grantedAuthorities);
    }
}
