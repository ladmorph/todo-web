package ru.ladmorph.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ladmorph.todo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User findByUsername(String username);
}
