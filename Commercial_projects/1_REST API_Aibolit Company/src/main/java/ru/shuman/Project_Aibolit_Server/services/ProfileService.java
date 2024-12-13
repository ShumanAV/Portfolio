package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Doctor;
import ru.shuman.Project_Aibolit_Server.models.Profile;
import ru.shuman.Project_Aibolit_Server.repositories.ProfileRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.time.LocalDateTime;
import java.util.Optional;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.copyNonNullProperties;

@Service
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    /*
    Внедрение зависимостей
     */
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
    public void create(Profile newProfile) {

        //зашифровываем пароль указанный в новом профиле и устанавливаем его в профиль
        String encodedPassword = passwordEncoder.encode(newProfile.getPassword());
        newProfile.setPassword(encodedPassword);

        //записываем в профиль дату и время создания и изменения
        newProfile.setCreatedAt(LocalDateTime.now());
        newProfile.setUpdatedAt(LocalDateTime.now());

        //добавляем профиль в список профилей для роли указанной в профиле
        roleService.addProfileAtListForRole(newProfile, newProfile.getRole());

        //сохраняем новый профиль в БД
        profileRepository.save(newProfile);
    }

    /*
    Метод update сохраняет измененный профиль, переносит все изменения в существующий профиль в БД:
    идентификатор и время создания остаются без изменений у существующего профиля,
    в существующем профиле в БД обновляется зашифрованный пароль из измененного профиля, т.к. он мог быть изменен,
    дата и время изменения, также для кэша - у роли добавляется измененнный профиль в список профилей,
    а также если роль была изменена у профиля, из списка профилей у старой роли удаляется существующий профиль.
    */
    @Transactional
    public void update(Profile updatedProfile) {

        //находим существующий профиль в БД по id
        Profile existingProfile = profileRepository.findById(updatedProfile.getId()).get();

        //для кэша, если в измененном профиле изменили роль, т.е. у существующего профиля и у измененного роль
        //отличается, то тогда удалим существующий профиль из списка профилей у старой роли
        if (!existingProfile.getRole().equals(updatedProfile.getRole())) {
            existingProfile.getRole().getProfiles().remove(existingProfile);
        }

        //зашифровываем пароль из измененного профиля и записываем его обратно в измененный профиль
        String encodedUpdatedPassword = passwordEncoder.encode(updatedProfile.getPassword());
        updatedProfile.setPassword(encodedUpdatedPassword);

        //копируем значения всех полей кроме тех, которые null из измененного профиля в существующий в БД
        copyNonNullProperties(updatedProfile, existingProfile);

        //обновляем дату и время изменения в существующем профиле
        existingProfile.setUpdatedAt(LocalDateTime.now());

        //для кэша, добавим или заменим измененный существующий профиль в списке профилей в указанной роли в профиле
        roleService.addProfileAtListForRole(existingProfile, existingProfile.getRole());
    }

    // Метод удаляет входящий профиль
    @Transactional
    public void delete(Profile profile) {
        profileRepository.delete(profile);
    }

}
