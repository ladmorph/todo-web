package ru.ladmorph.todo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ladmorph.todo.dto.UserDto;
import ru.ladmorph.todo.model.User;
import ru.ladmorph.todo.service.UserService;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {

        userService.save(userService.fromDto(userDto));

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") long id) {
        User user = userService.findById(id);
        log.debug("The user request: {}", user);
        return new ResponseEntity<>(userService.toDto(user), HttpStatus.OK);
    }

    /**
     *
     * @param userDto
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable Long id) {
        User userFromDb = userService.findById(id);
        User user = userService.fromDto(userDto);

        userService.copyProperties(user, userFromDb);
        userService.update(userFromDb);
        log.debug("User with id: {} is updated", user.getId());
        return new ResponseEntity<>(userService.toDto(user), HttpStatus.OK);
    }

    /**
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        User user = userService.findById(id);
        userService.delete(user);
        log.debug("The user {} is deleted", user);
    }
}

