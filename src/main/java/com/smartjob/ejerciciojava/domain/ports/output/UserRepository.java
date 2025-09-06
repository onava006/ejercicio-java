package com.smartjob.ejerciciojava.domain.ports.output;

import java.util.Optional;
import com.smartjob.ejerciciojava.domain.model.User;


public interface UserRepository {

    User saveUser(User user);

    Boolean findByEmail(String email);
}
