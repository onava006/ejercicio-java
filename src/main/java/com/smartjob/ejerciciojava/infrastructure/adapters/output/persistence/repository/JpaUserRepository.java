package com.smartjob.ejerciciojava.infrastructure.adapters.output.persistence.repository;

import com.smartjob.ejerciciojava.infrastructure.adapters.output.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
}
