package com.smartjob.ejerciciojava.application.adapters.input.mapper;

import com.smartjob.ejerciciojava.application.dto.UserDto;
import com.smartjob.ejerciciojava.domain.model.User;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.UserRegistrationRequest;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.UserRegistrationResponse;
import org.springframework.stereotype.Component;


public class UserMapper{

    public static  User toUser(UserRegistrationRequest value) {
        return null;
    }

    public static UserRegistrationResponse toUserResponse(User value) {
        return null;
    }

    public static UserDto toUserDto(User value){
        return null;
    }
}
