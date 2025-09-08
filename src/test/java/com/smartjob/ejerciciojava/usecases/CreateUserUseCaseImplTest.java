package com.smartjob.ejerciciojava.usecases;

import com.smartjob.ejerciciojava.application.adapters.input.CreateUserUseCaseImpl;
import com.smartjob.ejerciciojava.application.adapters.input.mapper.UserMapper;
import com.smartjob.ejerciciojava.application.ports.output.TokenProvider;
import com.smartjob.ejerciciojava.application.utils.UserEmailValidator;
import com.smartjob.ejerciciojava.domain.exception.InvalidEmailException;
import com.smartjob.ejerciciojava.domain.model.User;
import com.smartjob.ejerciciojava.domain.ports.input.UserService;
import com.smartjob.ejerciciojava.domain.ports.output.UserRepository;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.UserRegistrationRequest;
import com.smartjob.ejerciciojava.infrastructure.adapters.input.rest.model.UserRegistrationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateUserUseCaseImpl Tests")
class CreateUserUseCaseImplTest {

    @Mock
    private UserEmailValidator emailValidator;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    private UserRegistrationRequest userRegistrationRequest;
    private User user;
    private User savedUser;
    private UserRegistrationResponse expectedResponse;

    @BeforeEach
    void setUp() {
        // Inyectar valores de properties usando ReflectionTestUtils
        ReflectionTestUtils.setField(createUserUseCase, "emailAlreadyUsedError", "Email already exists");
        ReflectionTestUtils.setField(createUserUseCase, "jwtExpirationTime", 3600);
        UUID uuid = UUID.randomUUID();


        // Preparar datos de prueba
        userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setName("John Doe");
        userRegistrationRequest.setEmail("john.doe@example.com");
        userRegistrationRequest.setPassword("SecurePassword123!");

        user = new User();
        user.setId(uuid);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("tokenizedPassword");

        savedUser = new User();
        savedUser.setId(uuid);
        savedUser.setName("John Doe");
        savedUser.setEmail("john.doe@example.com");
        savedUser.setPassword("tokenizedPassword");

        expectedResponse = new UserRegistrationResponse();
        expectedResponse.setId(uuid);
        expectedResponse.name("John Doe");
        expectedResponse.email("john.doe@example.com");
        expectedResponse.token("jwt-token");
    }

    @Test
    @DisplayName("Should register user successfully when email is available")
    void shouldRegisterUserSuccessfully() {
        // Given
        String tokenizedPassword = "tokenizedPassword";

        // Mocks con MockedStatic para métodos estáticos
        try (MockedStatic<UserMapper> userMapperMock = mockStatic(UserMapper.class)) {

            // Configurar mocks
            doNothing().when(emailValidator).validateEmail(userRegistrationRequest.getEmail());
            when(userRepository.findByEmail(userRegistrationRequest.getEmail())).thenReturn(true);

            userMapperMock.when(() -> UserMapper.toUser(userRegistrationRequest)).thenReturn(user);
            doNothing().when(userService).configureNewUser(user);

            when(tokenProvider.createJwt(user.getPassword(), user.getName(), 3600))
                    .thenReturn(tokenizedPassword);

            when(userRepository.saveUser(any(User.class))).thenReturn(savedUser);
            userMapperMock.when(() -> UserMapper.toResponse(savedUser)).thenReturn(expectedResponse);

            // When
            UserRegistrationResponse result = createUserUseCase.register(userRegistrationRequest);

            // Then
            assertNotNull(result);
            assertEquals(expectedResponse.getId(), result.getId());
            assertEquals(expectedResponse.getName(), result.getName());
            assertEquals(expectedResponse.getEmail(), result.getEmail());
            assertEquals(expectedResponse.getToken(), result.getToken());

            // Verificaciones
            verify(emailValidator).validateEmail(userRegistrationRequest.getEmail());
            verify(userRepository).findByEmail(userRegistrationRequest.getEmail());
            verify(userService).configureNewUser(user);
            verify(tokenProvider).createJwt(user.getPassword(), user.getName(), 3600);
            verify(userRepository).saveUser(argThat(u ->
                    u.getEmail().equals(user.getEmail()) &&
                            u.getName().equals(user.getName()) &&
                            u.getPassword().equals(tokenizedPassword)
            ));

            userMapperMock.verify(() -> UserMapper.toUser(userRegistrationRequest));
            userMapperMock.verify(() -> UserMapper.toResponse(savedUser));
        }
    }

    @Test
    @DisplayName("Should throw InvalidEmailException when email already exists")
    void shouldThrowInvalidEmailExceptionWhenEmailAlreadyExists() {
        // Given
        doNothing().when(emailValidator).validateEmail(userRegistrationRequest.getEmail());
        when(userRepository.findByEmail(userRegistrationRequest.getEmail())).thenReturn(false);

        // When & Then
        InvalidEmailException exception = assertThrows(
                InvalidEmailException.class,
                () -> createUserUseCase.register(userRegistrationRequest)
        );

        assertEquals("Email already exists", exception.getMessage());

        // Verificaciones
        verify(emailValidator).validateEmail(userRegistrationRequest.getEmail());
        verify(userRepository).findByEmail(userRegistrationRequest.getEmail());

        // Verificar que no se llamaron métodos posteriores
        verifyNoInteractions(userService);
        verifyNoInteractions(tokenProvider);
        verify(userRepository, never()).saveUser(any());
    }

    @Test
    @DisplayName("Should propagate validation exception when email validation fails")
    void shouldPropagateValidationExceptionWhenEmailValidationFails() {
        // Given
        RuntimeException validationException = new RuntimeException("Invalid email format");
        doThrow(validationException).when(emailValidator).validateEmail(userRegistrationRequest.getEmail());

        // When & Then
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> createUserUseCase.register(userRegistrationRequest)
        );

        assertEquals("Invalid email format", exception.getMessage());

        // Verificaciones
        verify(emailValidator).validateEmail(userRegistrationRequest.getEmail());

        // Verificar que no se llamaron métodos posteriores
        verifyNoInteractions(userRepository);
        verifyNoInteractions(userService);
        verifyNoInteractions(tokenProvider);
    }

    @Test
    @DisplayName("Should handle repository exception during save")
    void shouldHandleRepositoryExceptionDuringSave() {
        // Given
        RuntimeException repositoryException = new RuntimeException("Database connection failed");

        try (MockedStatic<UserMapper> userMapperMock = mockStatic(UserMapper.class)) {

            doNothing().when(emailValidator).validateEmail(userRegistrationRequest.getEmail());
            when(userRepository.findByEmail(userRegistrationRequest.getEmail())).thenReturn(true);

            userMapperMock.when(() -> UserMapper.toUser(userRegistrationRequest)).thenReturn(user);
            doNothing().when(userService).configureNewUser(user);

            when(tokenProvider.createJwt(user.getPassword(), user.getName(), 3600))
                    .thenReturn("tokenizedPassword");

            when(userRepository.saveUser(any(User.class))).thenThrow(repositoryException);

            // When & Then
            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> createUserUseCase.register(userRegistrationRequest)
            );

            assertEquals("Database connection failed", exception.getMessage());

            // Verificaciones
            verify(emailValidator).validateEmail(userRegistrationRequest.getEmail());
            verify(userRepository).findByEmail(userRegistrationRequest.getEmail());
            verify(userService).configureNewUser(user);
            verify(tokenProvider).createJwt(user.getPassword(), user.getName(), 3600);
            verify(userRepository).saveUser(any(User.class));
        }
    }

    @Test
    @DisplayName("Should handle token generation exception")
    void shouldHandleTokenGenerationException() {
        // Given
        RuntimeException tokenException = new RuntimeException("Token generation failed");

        try (MockedStatic<UserMapper> userMapperMock = mockStatic(UserMapper.class)) {

            doNothing().when(emailValidator).validateEmail(userRegistrationRequest.getEmail());
            when(userRepository.findByEmail(userRegistrationRequest.getEmail())).thenReturn(true);

            userMapperMock.when(() -> UserMapper.toUser(userRegistrationRequest)).thenReturn(user);
            doNothing().when(userService).configureNewUser(user);

            when(tokenProvider.createJwt(user.getPassword(), user.getName(), 3600))
                    .thenThrow(tokenException);

            // When & Then
            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> createUserUseCase.register(userRegistrationRequest)
            );

            assertEquals("Token generation failed", exception.getMessage());

            // Verificaciones
            verify(emailValidator).validateEmail(userRegistrationRequest.getEmail());
            verify(userRepository).findByEmail(userRegistrationRequest.getEmail());
            verify(userService).configureNewUser(user);
            verify(tokenProvider).createJwt(user.getPassword(), user.getName(), 3600);

            // Verificar que no se guardó el usuario
            verify(userRepository, never()).saveUser(any());
        }
    }

    @Test
    @DisplayName("Should use correct JWT expiration time from properties")
    void shouldUseCorrectJwtExpirationTimeFromProperties() {
        // Given
        ReflectionTestUtils.setField(createUserUseCase, "jwtExpirationTime", 7200); // 2 horas

        try (MockedStatic<UserMapper> userMapperMock = mockStatic(UserMapper.class)) {

            doNothing().when(emailValidator).validateEmail(userRegistrationRequest.getEmail());
            when(userRepository.findByEmail(userRegistrationRequest.getEmail())).thenReturn(true);

            userMapperMock.when(() -> UserMapper.toUser(userRegistrationRequest)).thenReturn(user);
            doNothing().when(userService).configureNewUser(user);

            when(tokenProvider.createJwt(user.getPassword(), user.getName(), 7200))
                    .thenReturn("tokenizedPassword");

            when(userRepository.saveUser(any(User.class))).thenReturn(savedUser);
            userMapperMock.when(() -> UserMapper.toResponse(savedUser)).thenReturn(expectedResponse);

            // When
            createUserUseCase.register(userRegistrationRequest);

            // Then
            verify(tokenProvider).createJwt(user.getPassword(), user.getName(), 7200);
        }
    }
}