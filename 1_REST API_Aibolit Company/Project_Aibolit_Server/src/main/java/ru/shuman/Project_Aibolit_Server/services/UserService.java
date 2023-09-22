package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.*;
import ru.shuman.Project_Aibolit_Server.repositories.UserRepository;
import ru.shuman.Project_Aibolit_Server.util.StandardMethods;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final ProfileService profileService;
    private final SpecializationService specializationService;

    @Autowired
    public UserService(UserRepository userRepository, ProfileService profileService, SpecializationService specializationService) {
        this.userRepository = userRepository;
        this.profileService = profileService;
        this.specializationService = specializationService;
    }

    // Метод осуществляет поиск пользователя по номеру телефона и возвращает его в оболочке Optional
    public Optional<User> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    // Метод осуществляет поиск пользователя по СНИЛСу и возвращает его в оболочке Optional
    public Optional<User> findBySnils(String snils) {
        return userRepository.findBySnils(snils);
    }

    // Метод осуществляет поиск пользователя по ИНН и возвращает его в оболочке Optional
    public Optional<User> findByInn(String inn) {
        return userRepository.findByInn(inn);
    }

    // Метод осуществляет поиск всех пользователей и возвращает их список
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Метод осуществляет поиск пользователей с published = True или False и возвращает их список
    public List<User> findAllByPublished(boolean published) {
        return userRepository.findByPublished(published);
    }

    // Метод осуществляет поиск пользователей с showInSchedule = True или False и возвращает их список
    public List<User> findAllByShowInSchedule(boolean showInSchedule) {
        return userRepository.findByShowInSchedule(showInSchedule);
    }

    // Метод осуществляет поиск пользователей с published = True или False и showInSchedule = True или False и возвращает их список
    public List<User> findAllByPublishedAndShowInSchedule(boolean published, boolean showInSchedule) {
        return userRepository.findByPublishedAndShowInSchedule(published, showInSchedule);
    }

    // Метод осуществляет поиск пользователя по id и возвращает его в оболочке Optional
    public Optional<User> findById(Integer userId) {
        return userRepository.findById(userId);
    }

    /*
    Метод public void register создает нового пользователя и профайл для него.

    В методе данный пользователь добавляется в список пользователей для объекта специализации из БД, для кэша.
    Устанавливается дата и время создания и изменения пользователя.

    В случае если у пользователя есть доступ к системе, т.е. AccessToSystem = true создается новый профайл для данного пользователя.

    Далее происходит сохранение пользователя при помощи userRepository.save(user),
    точнее получаем для пользователя id из БД.
     */
    @Transactional
    public void register(User user) {

        specializationService.setUsersForSpecialization(user, user.getSpecialization());

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        if (user.isAccessToSystem()) {
            user.getProfile().setUser(user);
            profileService.create(user.getProfile());
        }

        userRepository.save(user);
    }

        /*
    Метод public void update осуществляет изменение существующего пользователя в БД.

    В методе данный пользователь добавляется в список пользователей для объекта специализации из БД, для кэша.
    Устанавливает дату и время изменения пользователя.
    Выполняет поиск профайла у данного пользователя.

    Далее, в случае если у входящего пользователя есть доступ к системе, т.е. AccessToSystem = True,
    если для данного пользователя уже есть профайл в БД, то происходит его изменение,
    если профайла нет, то происходит создание и сохранение нового профайла,
    точнее получение id для нового профайла.
    Делается это без каскадирования, т.е. отдельно для профайла.

    Если AccessToSystem = False, но при этом есть уже профайл в БД, происходит его удаление.

    Далее происходит сохранение обновленного пользователя.
     */
    @Transactional
    public void update(User user) {

        specializationService.setUsersForSpecialization(user, user.getSpecialization());

        user.setUpdatedAt(LocalDateTime.now());

        Profile existingProfile = userRepository.findById(user.getId()).get().getProfile();

        if (user.isAccessToSystem()) {

            user.getProfile().setUser(user);
            if (existingProfile != null) {
                profileService.update(existingProfile, user.getProfile());
            } else {
                profileService.create(user.getProfile());
            }

        } else {

            if (existingProfile != null) {
                profileService.delete(existingProfile);
            }
        }

        userRepository.save(user);
    }

    public void setCallingsForUser(Calling calling, User user) {
        StandardMethods.addObjectOneInListForObjectTwo(calling, user, this);
    }

    public void setContractsForUser(Contract contract, User user) {
        StandardMethods.addObjectOneInListForObjectTwo(contract, user, this);
    }
}
