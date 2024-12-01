package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.shuman.Project_Aibolit_Server.models.Profile;
import ru.shuman.Project_Aibolit_Server.repositories.ProfileRepository;
import ru.shuman.Project_Aibolit_Server.security.ProfileDetails;

import java.util.Optional;

@Service
public class ProfileDetailsService implements UserDetailsService {

    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileDetailsService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Profile> profile = profileRepository.findByUsername(username);

        if (profile.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }
        return new ProfileDetails(profile.get());
    }
}
