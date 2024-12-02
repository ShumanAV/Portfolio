package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Doctor;
import ru.shuman.Project_Aibolit_Server.models.Profile;
import ru.shuman.Project_Aibolit_Server.repositories.ProfileRepository;

import java.time.LocalDateTime;
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

    // Метод осуществляет поиск профиля по имени пользователя (username) и возвращает профиль в обертке Optional
    public Optional<Profile> findByUsername(String username) {
        return profileRepository.findByUsername(username);
    }

    // Метод осуществляет поиск профиля по id и возвращает профиль в обертке Optional
    public Optional<Profile> findById(Integer profileId) {
        return profileRepository.findById(profileId);
    }

    // Метод осуществляет поиск профиля по доктору, точнее по его id и возвращает профиль в обертке Optional
    public Optional<Profile> findByDoctor(Doctor doctor) {
        return profileRepository.findByDoctor(doctor);
    }

    /*
    Метод create создает новый профиль, точнее заполняет входящий профиль с формы данными:
    зашифрованный пароль, дата и время создания и изменения, также для выявления
    наличия списка профилей у роли и для кэша - у роли добавляется в список профилей данный профиль.
     */
    @Transactional
    public void create(Profile profile) {

        String encodedPassword = passwordEncoder.encode(profile.getPassword());
        profile.setPassword(encodedPassword);

        profile.setCreatedAt(LocalDateTime.now());
        profile.setUpdatedAt(LocalDateTime.now());

        roleService.addProfileAtListForRole(profile, profile.getRole());

        profileRepository.save(profile);
    }

    /*
    Метод update обновляет входящий с формы профиль, точнее заполняет данный профиль данными:
    идентификатор, берется у существующего профиля из БД, если пароль у входящего профиля отличается от пароля в БД,
    то обновляется зашифрованный пароль, дата и время изменения, также для выявления
    наличия списка профилей у роли и для кэша - у роли добавляется в список профилей данный профиль.
    Профиль сохраняется.
    */
    @Transactional
    public void update(Profile existingProfile, Profile updatedProfile) {

        updatedProfile.setId(existingProfile.getId());

        if (!existingProfile.getPassword().equals(updatedProfile.getPassword())) {
            String encodedPassword = passwordEncoder.encode(updatedProfile.getPassword());
            updatedProfile.setPassword(encodedPassword);
        }

        updatedProfile.setUpdatedAt(LocalDateTime.now());

        roleService.addProfileAtListForRole(updatedProfile, updatedProfile.getRole());

        profileRepository.save(updatedProfile);
    }

    // Метод удаляет входящий профиль
    @Transactional
    public void delete(Profile profile) {
        profileRepository.delete(profile);
    }

}
