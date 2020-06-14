package ru.ladmorph.todo.service;

import ru.ladmorph.todo.dto.UserDto;
import ru.ladmorph.todo.model.User;

public interface UserService {

    /**
     *
     * @param user
     * @return
     */
    long save(User user);

    /**
     *
     * @param user
     * @return
     */
    boolean update(User user);

    /**
     *
     * @param user
     * @return
     */
    boolean delete(User user);

    /**
     *
     * @param id
     * @return
     */
    User findById(Long id);

    /**
     *
     * @param user
     * @return
     */
    boolean checkingForDuplicate(User user);

    /**
     *
     * @param userDto
     * @return
     */
    User fromDto(UserDto userDto);

    /**
     *
     * @param user
     * @return
     */
    UserDto toDto(User user);

    /**
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * All the properties of source are copied to target
     * @param source
     * @param target
     */
    void copyProperties(User source, User target);
}
