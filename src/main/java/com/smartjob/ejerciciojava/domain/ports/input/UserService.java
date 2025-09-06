package com.smartjob.ejerciciojava.domain.ports.input;

import com.smartjob.ejerciciojava.application.adapters.input.mapper.UserMapper;
import com.smartjob.ejerciciojava.domain.model.User;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.UserRegistrationRequest;

public interface UserService {

     void configureNewUser(User userRequest);

     void changeStatus(User userRequest);
}
