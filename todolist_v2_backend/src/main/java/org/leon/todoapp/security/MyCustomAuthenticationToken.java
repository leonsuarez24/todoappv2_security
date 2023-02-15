package org.leon.todoapp.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MyCustomAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1L;
    private final MyUser myUser;

    public MyCustomAuthenticationToken(Collection<? extends GrantedAuthority> authorities, MyUser myUser) {
        super(authorities);
        this.myUser = myUser;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return myUser;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return myUser.getAuthorities();
    }

    @Override
    public boolean isAuthenticated() {
        return !myUser.getAuthorities().isEmpty();
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        throw new IllegalArgumentException("Dont do this");
    }

    @Override
    public String getName() {
        return myUser.getUsername();
    }

    @Override
    public Object getDetails() {
        return myUser.getUsername();
    }
}
