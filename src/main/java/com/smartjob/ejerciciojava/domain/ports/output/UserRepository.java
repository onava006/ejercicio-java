package com.smartjob.ejerciciojava.domain.ports.output;

import java.util.Optional;
import com.smartjob.ejerciciojava.domain.model.User;


public interface UserRepository {

    Optional<User> saveUser(User user);
}
