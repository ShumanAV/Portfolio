package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Doctor;
import ru.shuman.Project_Aibolit_Server.models.User;
import ru.shuman.Project_Aibolit_Server.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    // Метод осуществляет поиск пользователя по имени пользователя (username) и возвращает пользователя в обертке Optional
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Метод осуществляет поиск пользователя по id пользователя и возвращает пользователя в обертке Optional
    public Optional<User> findById(Integer userId) {
        return userRepository.findById(userId);
    }

    // Метод осуществляет поиск пользователя по доктору, точнее по его id и возвращает пользователя в обертке Optional
    public Optional<User> findByUser(Doctor doctor) {
        return userRepository.findByDoctor(doctor);
    }

    /*
    Метод create создает нового пользователя, точнее заполняет входящего пользователя с формы данными:
    зашифрованный пароль, дата и время создания и изменения пользователя, также для выявления
    наличия списка пользователей у роли и для кэша - у роли добавляется в список пользователей данный пользователь.
     */
    @Transactional
    public void create(User user) {

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        roleService.addUserAtListForRole(user, user.getRole());

        userRepository.save(user);
    }

    /*
    Метод update обновляет входящего с формы пользователя, точнее заполняет данного пользователя данными:
    идентификатор, берется у существующего пользователя из БД, если пароль у входящего пользователя отличается от пароля в БД,
    то обновляется зашифрованный пароль, дата и время изменения пользователя, также для выявления
    наличия списка пользователей у роли и для кэша - у роли добавляется в список пользователей данный пользователь.
    Пользователь сохраняется.
    */
    @Transactional
    public void update(User existingUser, User updatedUser) {

        updatedUser.setId(existingUser.getId());

        if (!existingUser.getPassword().equals(updatedUser.getPassword())) {
            String encodedPassword = passwordEncoder.encode(updatedUser.getPassword());
            updatedUser.setPassword(encodedPassword);
        }

        updatedUser.setUpdatedAt(LocalDateTime.now());

        roleService.addUserAtListForRole(updatedUser, updatedUser.getRole());

        userRepository.save(updatedUser);
    }

    // Метод удаляет входящего пользователя
    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }

}
