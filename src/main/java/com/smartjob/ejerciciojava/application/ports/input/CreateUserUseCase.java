package com.smartjob.ejerciciojava.application.ports.input;

import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.UserRegistrationRequest;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.UserRegistrationResponse;

public interface CreateUserUseCase  {

    UserRegistrationResponse register(UserRegistrationRequest userRegistration);

}
