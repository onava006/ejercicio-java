package com.smartjob.ejerciciojava.infrastructure.adapters.output.persistence.repository;

import com.smartjob.ejerciciojava.infrastructure.adapters.output.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
}
