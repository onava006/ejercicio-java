package com.smartjob.ejerciciojava.domain.ports.output;

import com.smartjob.ejerciciojava.domain.model.Phone;

import java.util.Optional;

public interface UserPhoneRepository {

    Optional<Phone> savePhone(Phone phone);
}
