package com.smartjob.ejerciciojava.domain.ports.output;

import com.smartjob.ejerciciojava.application.utils.UserEmailValidator;

import java.util.Optional;

public interface UserEmailRepository {

    Optional<UserEmailValidator> findUserEmailByName(String email);
}
