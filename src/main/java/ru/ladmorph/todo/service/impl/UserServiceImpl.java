package ru.ladmorph.todo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ladmorph.todo.dto.UserDto;
import ru.ladmorph.todo.exception.UserDuplicateException;
import ru.ladmorph.todo.model.Role;
import ru.ladmorph.todo.model.Task;
import ru.ladmorph.todo.model.User;
import ru.ladmorph.todo.repository.UserRepository;
import ru.ladmorph.todo.service.RoleService;
import ru.ladmorph.todo.service.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String ROLE_USER = "USER";

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public long save(User user) {
        if (checkingForDuplicate(user)) {
            throw new UserDuplicateException("User with the username: " + user.getUsername() +
                    " or email: " + user.getEmail() + " is registered.");
        }
        user.setTaskList(Collections.EMPTY_LIST);
        user.setUpdatedDate(LocalDateTime.now());
        user.setCreationDate(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList(roleService.findByRoleName(ROLE_USER)));

        User userAfterSave = userRepository.save(user);
        log.info("User with the username {} has registered.", user.getUsername());
        return userAfterSave.getId();
    }

    @Override
    public boolean update(User user) {
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean delete(User user) {
        userRepository.delete(user);
        return true;
    }

    @Override
    public User findById(Long id) {
        Optional<User> userFromDb = userRepository.findById(id);

        if (userFromDb.isPresent()) {
            return userFromDb.get();
        }
        throw new UsernameNotFoundException("User with id: " + id + " doesn't exists");
    }

    @Override
    public boolean checkingForDuplicate(User user) {
        String username = user.getUsername();
        String email = user.getEmail();

        return userRepository.existsByEmail(email) || userRepository.existsByUsername(username);
    }

    @Override
    public User fromDto(UserDto userDto) {
        User user = new User();

        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());

        return user;
    }

    @Override
    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());

        return userDto;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void copyProperties(User source, User target) {
        Long id = source.getId();
        String username = source.getUsername();
        String email = source.getEmail();
        String password = source.getPassword();
        List<Role> roleList = source.getRoles();
        List<Task> taskList = source.getTaskList();

        if (id != null) {
            target.setId(id);
        }

        if (username != null) {
            target.setUsername(source.getUsername());
        }

        if (email != null) {
            target.setEmail(source.getEmail());
        }

        if (password != null) {
            target.setPassword(source.getPassword());
        }

        if (!roleList.isEmpty()) {
            target.setRoles(source.getRoles());
        }

        if (!taskList.isEmpty()) {
            target.setTaskList(source.getTaskList());
        }

        target.setUpdatedDate(LocalDateTime.now());
    }
}
