package com.smartjob.ejerciciojava.infrastructure.adapters.input.rest;

import com.smartjob.ejerciciojava.application.adapters.input.mapper.UserMapper;
import com.smartjob.ejerciciojava.application.dto.UserDto;
import com.smartjob.ejerciciojava.application.ports.input.CreateUserUseCase;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.api.UserDetailsApi;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.UserRegistrationRequest;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.UserRegistrationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController implements UserDetailsApi {

    CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase){
        this.createUserUseCase = createUserUseCase;
    }


    @Override
    public ResponseEntity<UserRegistrationResponse> registerUser(UserRegistrationRequest userRegistrationRequest) {
        UserRegistrationResponse userCreationResponse = createUserUseCase.register(userRegistrationRequest);
       return new ResponseEntity<>(userCreationResponse, HttpStatus.OK);

    }
}
