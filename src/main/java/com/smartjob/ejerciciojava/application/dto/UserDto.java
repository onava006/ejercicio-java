package com.smartjob.ejerciciojava.application.dto;

import com.smartjob.ejerciciojava.domain.model.Phone;

import java.sql.Date;
import java.util.List;

public record UserDto(
        Long id,
        String name,
        String email,
        String password,
        List<Phone>phones,
        Date creationDate,
        Date lastModificationDate,
        boolean isActive) {
}
