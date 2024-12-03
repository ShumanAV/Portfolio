package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shuman.Project_Aibolit_Server.dto.RegionDTO;
import ru.shuman.Project_Aibolit_Server.dto.RoleDTO;
import ru.shuman.Project_Aibolit_Server.models.Region;
import ru.shuman.Project_Aibolit_Server.models.Role;
import ru.shuman.Project_Aibolit_Server.services.RoleService;
import ru.shuman.Project_Aibolit_Server.util.validators.RoleIdValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.RoleValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;
    private final RoleIdValidator roleIdValidator;
    private final RoleValidator roleValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleController(RoleService roleService, RoleIdValidator roleIdValidator, RoleValidator roleValidator,
                          ModelMapper modelMapper) {
        this.roleService = roleService;
        this.roleIdValidator = roleIdValidator;
        this.roleValidator = roleValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> sendListRoles() {

        List<Role> roles = roleService.findAll();

        List<RoleDTO> roleDTOList = roles.stream().map(this::convertToRoleDTO).collect(Collectors.toList());

        return new ResponseEntity<>(roleDTOList, HttpStatus.OK);
    }

    // Метод конверсии из DTO в модель
    private Role convertToRole(RoleDTO roleDTO) {
        return this.modelMapper.map(roleDTO, Role.class);
    }

    // Метод конверсии из модели в DTO
    private RoleDTO convertToRoleDTO(Role role) {
        return this.modelMapper.map(role, RoleDTO.class);
    }
}
