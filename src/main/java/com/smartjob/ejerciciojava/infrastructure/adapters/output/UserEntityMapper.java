package com.smartjob.ejerciciojava.infrastructure.adapters.output;

import com.smartjob.ejerciciojava.domain.model.Phone;
import com.smartjob.ejerciciojava.domain.model.User;
import com.smartjob.ejerciciojava.infrastructure.adapters.output.entities.PhoneEntity;
import com.smartjob.ejerciciojava.infrastructure.adapters.output.entities.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class UserEntityMapper {

    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        User user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());

        // Mapeo de phones
        if (entity.getPhones() != null) {
            List<Phone> phones = entity.getPhones().stream()
                    .map(phoneEntity -> mapPhone(phoneEntity))
                    .collect(Collectors.toList());
            user.setPhones(phones);
        }

        user.setCreationDate(entity.getCreatedAt());
        user.setLastModificationDate(entity.getModifiedAt());
        user.setLastLogin(entity.getLastLogin());


        user.setActive(true);

        return user;
    }

    private static Phone mapPhone(PhoneEntity phoneEntity) {
        if (phoneEntity == null) {
            return null;
        }

        Phone phone = new Phone();
        phone.setNumber(String.valueOf(phoneEntity.getNumber()));
        phone.setCityCode(String.valueOf(phoneEntity.getCityCode()));
        phone.setCountryCode(String.valueOf(phoneEntity.getCountryCode()));
        return phone;
    }

    public static UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());

        if (user.getPhones() != null) {
            List<PhoneEntity> phoneEntities = user.getPhones().stream()
                    .map(phone -> mapPhoneEntity(phone))
                    .collect(Collectors.toList());
            phoneEntities.forEach(p -> p.setUser(entity));
            entity.setPhones(phoneEntities);
        }

        entity.setCreatedAt(user.getCreationDate());
        entity.setModifiedAt(user.getLastModificationDate());
        entity.setLastLogin(user.getLastLogin()); // opcional, si quieres inicializar

        return entity;
    }

    private static PhoneEntity mapPhoneEntity(Phone phone) {
        if (phone == null) {
            return null;
        }
        PhoneEntity phoneEntity = new PhoneEntity();
        phoneEntity.setNumber(Long.valueOf(phone.getNumber()));
        phoneEntity.setCityCode(Integer.valueOf(phone.getCityCode()));
        phoneEntity.setCountryCode(Integer.valueOf(phone.getCountryCode()));
        return phoneEntity;
    }
}



