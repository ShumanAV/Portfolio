package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Profile;
import ru.shuman.Project_Aibolit_Server.models.Role;
import ru.shuman.Project_Aibolit_Server.repositories.RoleRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.util.List;
import java.util.Optional;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.addObjectOneInListForObjectTwo;

@Service
@Transactional(readOnly = true)
public class RoleService {

    private final RoleRepository roleRepository;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /*
    Метод формирует и возвращает список всех ролей
     */
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    /*
    Метод ищет роль по имени и возвращает ее в обертке Optional
     */
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    /*
    Метод ищет роль по id и возвращает ее в обертке Optional
     */
    public Optional<Role> findById(Integer roleId) {
        return roleRepository.findById(roleId);
    }

    /*
    Метод сохраняет новую роль в БД
     */
    @Transactional
    public void create(Role newRole) {
        roleRepository.save(newRole);
    }

    /*
    Метод сохраняет измененную роль в БД
     */
    @Transactional
    public void update(Role updatedRole) {
        roleRepository.save(updatedRole);
    }

    /*
    Метод добавляет профиль в список профилей для роли указанной в профиле, делается это для кэша
     */
    public void addProfileAtListForRole(Profile profile, Role role) {
        addObjectOneInListForObjectTwo(profile, role, this);
    }
}
