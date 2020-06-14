package ru.ladmorph.todo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ladmorph.todo.model.User;
import ru.ladmorph.todo.service.UserService;

@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);

        if (user != null) {
            return new UserPrincipal(user);
        }
        throw new UsernameNotFoundException("User with username: " + user.getUsername() + " doesn't exists.");
    }
}
