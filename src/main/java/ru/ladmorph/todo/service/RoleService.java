package ru.ladmorph.todo.service;

import ru.ladmorph.todo.model.Role;

public interface RoleService {

    /**
     *
     * @param roleName
     * @return
     */
    Role findByRoleName(String roleName);
}
