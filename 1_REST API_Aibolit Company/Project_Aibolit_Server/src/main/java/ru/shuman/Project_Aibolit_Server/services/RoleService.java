package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Profile;
import ru.shuman.Project_Aibolit_Server.models.Role;
import ru.shuman.Project_Aibolit_Server.repositories.RoleRepository;
import ru.shuman.Project_Aibolit_Server.util.StandardMethods;

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
    Метод private void setProfilesForRole(Profile profile) принимает на вход профайл, далее для данного профайла
    находится роль из БД, проверяется есть ли список профайлов у данной роли, если он равен null, то создается
    new ArrayList<>(), а если список уже есть, то добавляется данный профайл.
     */

    public void setProfilesForRole(Profile profile, Role role) {
        StandardMethods.addObjectOneInListForObjectTwo(profile, role, this);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
