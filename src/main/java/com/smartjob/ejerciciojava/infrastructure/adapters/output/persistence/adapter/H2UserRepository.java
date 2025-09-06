package com.smartjob.ejerciciojava.infrastructure.adapters.output.persistence.adapter;

import com.smartjob.ejerciciojava.domain.model.User;
import com.smartjob.ejerciciojava.domain.ports.output.UserRepository;
import com.smartjob.ejerciciojava.infrastructure.adapters.output.persistence.repository.JpaUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class H2UserRepository implements UserRepository {

final JpaUserRepository jpaUserRepository;

public H2UserRepository(JpaUserRepository jpaUserRepository){
this.jpaUserRepository = jpaUserRepository;
}


    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }
}
