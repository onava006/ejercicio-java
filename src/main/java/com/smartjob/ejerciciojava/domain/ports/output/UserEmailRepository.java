package com.smartjob.ejerciciojava.domain.ports.output;

import com.smartjob.ejerciciojava.domain.valueobjects.UserEmail;

import java.util.Optional;

public interface UserEmailRepository {

    Optional<UserEmail> findUserEmailByName(String email);
}
