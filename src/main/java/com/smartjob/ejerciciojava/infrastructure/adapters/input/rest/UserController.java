package com.smartjob.ejerciciojava.infrastructure.adapters.input.rest;

import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.api.UserDetailsApi;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.UserRegistrationRequest;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.UserRegistrationResponse;
import org.springframework.http.ResponseEntity;

public class UserController implements UserDetailsApi {

    @Override
    public ResponseEntity<UserRegistrationResponse> registerUser(UserRegistrationRequest userRegistrationRequest) {
        return null;
    }
}
