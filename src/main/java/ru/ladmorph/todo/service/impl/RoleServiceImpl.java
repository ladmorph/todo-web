package ru.ladmorph.todo.service.impl;

import org.springframework.stereotype.Service;
import ru.ladmorph.todo.model.Role;
import ru.ladmorph.todo.repository.RoleRepository;
import ru.ladmorph.todo.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
