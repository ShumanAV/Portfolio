package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.User;
import ru.shuman.Project_Aibolit_Server.models.Role;
import ru.shuman.Project_Aibolit_Server.repositories.RoleRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    public Optional<Role> findById(Integer roleId) {
        return roleRepository.findById(roleId);
    }

    @Transactional
    public void create(Role role) {
        roleRepository.save(role);
    }

    @Transactional
    public void update(Role role) {
        roleRepository.save(role);
    }

    /*
    Метод принимает на вход пользователя, далее для данного пользователя
    находится роль из БД, проверяется есть ли список пользователей у данной роли, если он равен null, то создается
    new ArrayList<>(), а если список уже есть, то добавляется данный пользователь.
     */

    public void addUserAtListForRole(User user, Role role) {
        GeneralMethods.addObjectOneInListForObjectTwo(user, role, this);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
