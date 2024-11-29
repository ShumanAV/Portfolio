package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.shuman.Project_Aibolit_Server.models.User;
import ru.shuman.Project_Aibolit_Server.repositories.UserRepository;
import ru.shuman.Project_Aibolit_Server.security.UserDetails;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }
        return new UserDetails(user.get());
    }
}
