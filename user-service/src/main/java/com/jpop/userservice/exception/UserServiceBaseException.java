package com.jpop.userservice.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserServiceBaseException extends RuntimeException {
    public UserServiceBaseException(String error){
        super(error);
    }
}
