package com.smartjob.ejerciciojava.application.ports.input;

import com.smartjob.ejerciciojava.application.adapters.input.CreateUserUseCaseImpl;
import com.smartjob.ejerciciojava.application.dto.UserDto;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.UserRegistrationRequest;

public interface CreateUserUseCase  {

        UserDto create(UserRegistrationRequest userRegistration);

}
