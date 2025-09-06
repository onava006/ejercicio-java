package com.smartjob.ejerciciojava.application.adapters.input.mapper;

import com.smartjob.ejerciciojava.application.dto.UserDto;
import com.smartjob.ejerciciojava.domain.model.Phone;
import com.smartjob.ejerciciojava.domain.model.User;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.ContactPhone;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.UserRegistrationRequest;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.UserRegistrationResponse;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;


public class UserMapper {

    public static User toUser(UserRegistrationRequest request) {
        if (request == null) {
            return null;
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        // Mapea la lista de teléfonos si existe
        if (request.getPhones() != null) {
            List<Phone> phones = request.getPhones().stream()
                    .map(phone -> UserMapper.mapPhone(phone))
                    .collect(Collectors.toList());
            user.setPhones(phones);
        }
        return user;
    }


    public static UserRegistrationResponse toResponse(User user) {

        if (user == null) {
            return null;
            //arrojar excepcion
        }

        UserRegistrationResponse response = new UserRegistrationResponse();

        response.setId(user.getId() != null ? user.getId().toString() : null);

        response.setName(user.getName());
        response.setEmail(user.getEmail());

        response.setPhones(mapPhones(user.getPhones()));

        response.setCreated(toLocalDateTime(user.getCreationDate()));
        response.setModified(toLocalDateTime(user.getLastModificationDate()));
        response.setLastLogin(toLocalDateTime(user.getLastModificationDate()));

        response.setToken(user.getPassword());
        response.setIsactive(user.isActive());

        return response;
    }

    private static Phone mapPhone(ContactPhone phone) {
        if (phone == null) {
            return null;
        }
        Phone mapped = new Phone();
        mapped.setNumber(phone.getNumber());
        mapped.setCityCode(phone.getCitycode());
        mapped.setCountryCode(phone.getContrycode());
        return mapped;
    }

    private static ContactPhone mapResponsePhone(Phone phone) {
        if (phone == null) {
            return null;
        }
        ContactPhone mapped = new ContactPhone();
        mapped.setNumber(phone.getNumber());
        mapped.setCitycode(phone.getCityCode());
        mapped.setContrycode(phone.getCountryCode());
        return mapped;
    }

    private static List<ContactPhone> mapPhones(List<Phone> phones) {
        if (phones == null) {
            return null;
        }
        return phones.stream()
                .map(phone -> mapResponsePhone(phone))
                .collect(Collectors.toList());
    }

    private static LocalDateTime toLocalDateTime(java.util.Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}




   

