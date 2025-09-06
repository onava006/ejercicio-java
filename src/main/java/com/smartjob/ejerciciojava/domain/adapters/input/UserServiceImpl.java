package com.smartjob.ejerciciojava.domain.adapters.input;

import com.smartjob.ejerciciojava.domain.model.User;
import com.smartjob.ejerciciojava.domain.ports.input.UserService;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.UserRegistrationRequest;

import java.sql.Date;

public class UserServiceImpl implements UserService {

    public  UserServiceImpl(){
    }

    @Override
    public void configureNewUser(User userRequest) {

        userRequest.setActive(true);
        userRequest.setCreationDate(new Date(System.currentTimeMillis()));
        userRequest.setLastModificationDate(new Date(System.currentTimeMillis()));
    }

    @Override
    public void changeStatus(User userRequest) {

        userRequest.setLastModificationDate(new Date(System.currentTimeMillis()));
        userRequest.setActive(!userRequest.isActive());
    }
}
