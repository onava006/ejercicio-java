package com.smartjob.ejerciciojava.application.adapters.input;

import com.smartjob.ejerciciojava.application.adapters.input.mapper.UserMapper;
import com.smartjob.ejerciciojava.application.ports.input.CreateUserUseCase;
import com.smartjob.ejerciciojava.application.ports.output.TokenProvider;
import com.smartjob.ejerciciojava.application.utils.UserEmailValidator;
import com.smartjob.ejerciciojava.domain.exception.InvalidEmailException;
import com.smartjob.ejerciciojava.domain.model.User;
import com.smartjob.ejerciciojava.domain.ports.input.UserService;
import com.smartjob.ejerciciojava.domain.ports.output.UserRepository;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.UserRegistrationRequest;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.UserRegistrationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    UserEmailValidator emailValidator;

    @Value("${com.smartjob.ejerciciojava.exception.error001}")
    String emailAlreadyUsedError;
    @Value("${com.smartjob.ejerciciojava.jwt.expirationtime}")
    Integer jwtExpirationTime;

    UserService userService;
    UserRepository userRepository;
   // UserPhoneRepository userPhoneRepository;
    TokenProvider tokenProvider;

    public CreateUserUseCaseImpl(UserService userService, UserRepository userRepository,  TokenProvider tokenProvider,UserEmailValidator emailValidator ) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.emailValidator = emailValidator;
       // this.userPhoneRepository = userPhoneRepository; , UserPhoneRepository userPhoneRepository
    }

    @Override
    public UserRegistrationResponse register(UserRegistrationRequest userRegistration) {

        String userEmail = userRegistration.getEmail();
        emailValidator.validateEmail(userEmail);
        Boolean userEmailIsAvailable = userRepository.findByEmail(userEmail);

        if(!userEmailIsAvailable) throw new InvalidEmailException(emailAlreadyUsedError);
        User newUser = UserMapper.toUser(userRegistration);
        userService.configureNewUser(newUser);
        String tokenizedPassword = tokenProvider.createJwt(newUser.getPassword(), newUser.getName(), jwtExpirationTime);
        newUser.setPassword(tokenizedPassword);
        return UserMapper.toResponse(userRepository.saveUser(newUser));
    }


}
