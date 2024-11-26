package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Profile;
import ru.shuman.Project_Aibolit_Server.models.Role;
import ru.shuman.Project_Aibolit_Server.models.User;
import ru.shuman.Project_Aibolit_Server.repositories.ProfileRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    // Метод осуществляет поиск профайла по имени пользователя (username) и возвращает профайл в обертке Optional
    public Optional<Profile> findByUsername(String username) {
        return profileRepository.findByUsername(username);
    }

    // Метод осуществляет поиск профайла по id профайла и возвращает профайл в обертке Optional
    public Optional<Profile> findById(Integer profileId) {
        return profileRepository.findById(profileId);
    }

    // Метод осуществляет поиск профайла по пользователю, точнее по его id и возвращает профайл в обертке Optional
    public Optional<Profile> findByUser(User user) {
        return profileRepository.findByUser(user);
    }

    /*
    Метод create создает новый профайл, точнее заполняет входящий профайл с формы данными:
    зашифрованный пароль, дата и время создания и изменения профайла, также для выявления
    наличия списка пользователей у роли и для кэша у роли добавляется в список пользователей данный пользователь.
     */
    @Transactional
    public void create(Profile profile) {

        String encodedPassword = passwordEncoder.encode(profile.getPassword());
        profile.setPassword(encodedPassword);

        profile.setCreatedAt(LocalDateTime.now());
        profile.setUpdatedAt(LocalDateTime.now());

        roleService.setProfilesForRole(profile, profile.getRole());

        profileRepository.save(profile);
    }

    /*
    Метод update обновляет входящий с формы профайл, точнее заполняет данный профайл данными:
    идентификатор, берется у существующего профайла из БД, если пароль у входящего профайла отличается от пароля в БД,
    то обновляется зашифрованный пароль, дата и время изменения профайла, также для выявления
    наличия списка профайлов у роли и для кэша у роли добавляется в список профайлов данный профайл.
    Профайл сохраняется.
    */
    @Transactional
    public void update(Profile existingProfile, Profile updatedProfile) {

        updatedProfile.setId(existingProfile.getId());

        if (!existingProfile.getPassword().equals(updatedProfile.getPassword())) {
            String encodedPassword = passwordEncoder.encode(updatedProfile.getPassword());
            updatedProfile.setPassword(encodedPassword);
        }

        updatedProfile.setUpdatedAt(LocalDateTime.now());

        roleService.setProfilesForRole(updatedProfile, updatedProfile.getRole());

        profileRepository.save(updatedProfile);
    }

    // Метод удаляет входящий профайл
    @Transactional
    public void delete(Profile profile) {
        profileRepository.delete(profile);
    }

}
