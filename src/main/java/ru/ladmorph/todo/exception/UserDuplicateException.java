package ru.ladmorph.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserDuplicateException extends RuntimeException {
    public UserDuplicateException() {
    }

    public UserDuplicateException(String message) {
        super(message);
    }
}
