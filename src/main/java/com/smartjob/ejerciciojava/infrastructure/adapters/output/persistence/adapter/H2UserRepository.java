package com.smartjob.ejerciciojava.infrastructure.adapters.output.persistence.adapter;

import com.smartjob.ejerciciojava.domain.model.User;
import com.smartjob.ejerciciojava.domain.ports.output.UserRepository;
import com.smartjob.ejerciciojava.infrastructure.adapters.output.UserEntityMapper;
import com.smartjob.ejerciciojava.infrastructure.adapters.output.entities.UserEntity;
import com.smartjob.ejerciciojava.infrastructure.adapters.output.persistence.repository.JpaUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class H2UserRepository implements UserRepository {

    final JpaUserRepository jpaUserRepository;

    public H2UserRepository(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User saveUser(User user) {

        UserEntity entityToSave = UserEntityMapper.toEntity(user);
        return UserEntityMapper.toDomain(jpaUserRepository.save(entityToSave));
    }

    @Override
    public Boolean findByEmail(String email) {

       Optional<UserEntity> userByEmail = jpaUserRepository.findByEmail(email);

        return userByEmail.isEmpty();
    }
}
