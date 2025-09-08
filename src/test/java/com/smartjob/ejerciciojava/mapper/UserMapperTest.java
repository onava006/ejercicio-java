package com.smartjob.ejerciciojava.mapper;

import com.smartjob.ejerciciojava.application.adapters.input.mapper.UserMapper;
import com.smartjob.ejerciciojava.domain.model.Phone;
import com.smartjob.ejerciciojava.domain.model.User;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.ContactPhone;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.UserRegistrationRequest;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.UserRegistrationResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

    @Test
    void toUser_nullRequest_returnsNull() {
        User result = UserMapper.toUser(null);
        assertNull(result, "Debe devolver null si la request es null");
    }

    @Test
    void toUser_basicFieldsMappedCorrectly() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setName("Juan");
        request.setEmail("juan@example.com");
        request.setPassword("secret");

        User result = UserMapper.toUser(request);

        assertNotNull(result);
        assertEquals("Juan", result.getName());
        assertEquals("juan@example.com", result.getEmail());
        assertEquals("secret", result.getPassword());
        assertTrue(result.getPhones().isEmpty(), "Si no se setean teléfonos debe estar vacío");    }

    @Test
    void toUser_withEmptyPhonesList_setsEmptyPhones() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setName("Ana");
        request.setEmail("ana@example.com");
        request.setPassword("12345");
        request.setPhones(Collections.emptyList());

        User result = UserMapper.toUser(request);

        assertNotNull(result.getPhones());
        assertTrue(result.getPhones().isEmpty(), "Debe mapear lista vacía correctamente");
    }

    @Test
    void toUser_withPhones_mapsPhonesCorrectly() {
        UserRegistrationRequest request = getUserRegistrationRequest();

        User result = UserMapper.toUser(request);

        assertNotNull(result.getPhones());
        assertEquals(2, result.getPhones().size());

        assertEquals("1111111", result.getPhones().get(0).getNumber());
        assertEquals("1", result.getPhones().get(0).getCityCode());
        assertEquals("57", result.getPhones().get(0).getCountryCode());

        assertEquals("2222222", result.getPhones().get(1).getNumber());
        assertEquals("2", result.getPhones().get(1).getCityCode());
        assertEquals("58", result.getPhones().get(1).getCountryCode());
    }

    private static UserRegistrationRequest getUserRegistrationRequest() {
        ContactPhone phone1 = new ContactPhone();
        phone1.setNumber("1111111");
        phone1.setCitycode("1");
        phone1.setCountrycode("57");

        ContactPhone phone2 = new ContactPhone();
        phone2.setNumber("2222222");
        phone2.setCitycode("2");
        phone2.setCountrycode("58");

        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setName("Pedro");
        request.setEmail("pedro@example.com");
        request.setPassword("password");
        request.setPhones(Arrays.asList(phone1, phone2));
        return request;
    }

    @Test
    void toResponse_nullUser_returnsNull() {
        UserRegistrationResponse result = UserMapper.toResponse(null);
        assertNull(result, "Debe devolver null si el user es null");
    }

    @Test
    void toResponse_basicFieldsMappedCorrectly() {
        UUID uuid = UUID.randomUUID();

        User user = new User();
        user.setId(uuid);
        user.setName("Juan");
        user.setEmail("juan@example.com");
        user.setPassword("secret");
        user.setActive(true);

        LocalDateTime now = LocalDateTime.now();
        user.setCreationDate(now);
        user.setLastModificationDate(now);

        // No seteamos teléfonos
        user.setPhones(null);

        UserRegistrationResponse result = UserMapper.toResponse(user);

        assertNotNull(result);
        assertEquals(uuid, result.getId());
        assertEquals("Juan", result.getName());
        assertEquals("juan@example.com", result.getEmail());
        assertEquals("secret", result.getToken());
        assertTrue(result.getIsactive());

        assertEquals(now, result.getCreated());
        assertEquals(now, result.getModified());
        assertEquals(now, result.getLastLogin());

        assertNull(result.getPhones(), "Si el usuario no tiene teléfonos, la respuesta también debe ser null");
    }

    @Test
    void toResponse_withEmptyPhones_setsEmptyPhones() {
        UUID uuid = UUID.randomUUID();

        User user = new User();
        user.setId(uuid);
        user.setName("Ana");
        user.setEmail("ana@example.com");
        user.setPassword("1234");
        user.setActive(false);
        user.setPhones(Collections.emptyList());

        UserRegistrationResponse result = UserMapper.toResponse(user);

        assertEquals(uuid, result.getId());
        assertNotNull(result.getPhones());
        assertTrue(result.getPhones().isEmpty(), "Debe mapear lista vacía correctamente");
    }

    @Test
    void toResponse_withPhones_mapsPhonesCorrectly() {
        UUID uuid = UUID.randomUUID();

        Phone phone1 = new Phone();
        phone1.setNumber("1111111");
        phone1.setCityCode("1");
        phone1.setCountryCode("57");

        Phone phone2 = new Phone();
        phone2.setNumber("2222222");
        phone2.setCityCode("2");
        phone2.setCountryCode("58");

        User user = new User();
        user.setId(uuid);
        user.setName("Pedro");
        user.setEmail("pedro@example.com");
        user.setPassword("pwd");
        user.setActive(true);
        user.setPhones(Arrays.asList(phone1, phone2));

        UserRegistrationResponse result = UserMapper.toResponse(user);

        assertEquals(uuid, result.getId());
        assertNotNull(result.getPhones());
        assertEquals(2, result.getPhones().size());

        assertEquals("1111111", result.getPhones().get(0).getNumber());
        assertEquals("1", result.getPhones().get(0).getCitycode());
        assertEquals("57", result.getPhones().get(0).getCountrycode());

        assertEquals("2222222", result.getPhones().get(1).getNumber());
        assertEquals("2", result.getPhones().get(1).getCitycode());
        assertEquals("58", result.getPhones().get(1).getCountrycode());
    }
}
