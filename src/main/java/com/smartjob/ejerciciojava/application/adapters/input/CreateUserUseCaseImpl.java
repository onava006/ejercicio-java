package com.smartjob.ejerciciojava.application.adapters.input;

import com.smartjob.ejerciciojava.application.adapters.input.mapper.UserMapper;
import com.smartjob.ejerciciojava.application.dto.UserDto;
import com.smartjob.ejerciciojava.application.ports.input.CreateUserUseCase;
import com.smartjob.ejerciciojava.application.utils.UserEmailValidator;
import com.smartjob.ejerciciojava.domain.exception.InvalidEmailException;
import com.smartjob.ejerciciojava.domain.model.User;
import com.smartjob.ejerciciojava.domain.ports.input.UserService;
import com.smartjob.ejerciciojava.domain.ports.output.UserPhoneRepository;
import com.smartjob.ejerciciojava.domain.ports.output.UserRepository;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.UserRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    @Autowired
    UserEmailValidator emailValidator;

    @Value("${com.smartjob.ejerciciojava.exception.error001}")
    String emailAlreadyUsedError;

    UserService userService;
    UserRepository userRepository;
   // UserPhoneRepository userPhoneRepository;

    public CreateUserUseCaseImpl(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
       // this.userPhoneRepository = userPhoneRepository; , UserPhoneRepository userPhoneRepository
    }

    @Override
    public UserDto create(UserRegistrationRequest userRegistration) {

        String userEmail = userRegistration.getEmail();
        emailValidator.validateEmail(userEmail);
        Boolean userEmailIsAvailable = userRepository.findByEmail(userEmail).isEmpty();
        if(!userEmailIsAvailable) throw new InvalidEmailException(emailAlreadyUsedError);

        User user = userService.configureNewUser(UserMapper.toUser(userRegistration));
        return UserMapper.toUserDto(userRepository.saveUser(user));
    }


}
