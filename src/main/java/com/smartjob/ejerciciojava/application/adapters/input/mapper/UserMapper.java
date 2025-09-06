package com.smartjob.ejerciciojava.application.adapters.input.mapper;

import com.smartjob.ejerciciojava.application.dto.UserDto;
import com.smartjob.ejerciciojava.domain.model.Phone;
import com.smartjob.ejerciciojava.domain.model.User;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.ContactPhone;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.UserRegistrationRequest;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.UserRegistrationResponse;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;


public class UserMapper{

    public static  User toUser(UserRegistrationRequest request) {
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

    public static UserRegistrationResponse toUserResponse(User value) {
        return null;
    }

    public static UserDto toUserDto(User value){
        return null;
    }
    }



