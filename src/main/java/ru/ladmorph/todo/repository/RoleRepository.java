package ru.ladmorph.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ladmorph.todo.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String roleName);
}
