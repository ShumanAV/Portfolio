package ru.shuman.Project_Aibolit_Server.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.shuman.Project_Aibolit_Server.models.Profile;

import java.util.Collection;
import java.util.Collections;

public class ProfileDetails implements UserDetails {

    private final Profile profile;

    public ProfileDetails(Profile profile) {
        this.profile = profile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(profile.getRole().getName()));
    }

    @Override
    public String getPassword() {
        return this.profile.getPassword();
    }

    @Override
    public String getUsername() {
        return this.profile.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Profile getProfile() {
        return profile;
    }
}
