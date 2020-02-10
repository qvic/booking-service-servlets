package com.epam.bookingservice.service.impl;

import com.epam.bookingservice.dao.UserDao;
import com.epam.bookingservice.domain.User;
import com.epam.bookingservice.entity.UserEntity;
import com.epam.bookingservice.mapper.Mapper;
import com.epam.bookingservice.mapper.RoleMapper;
import com.epam.bookingservice.mapper.UserMapper;
import com.epam.bookingservice.service.encryptor.PasswordEncryptor;
import com.epam.bookingservice.service.exception.UserAlreadyExistsException;
import com.epam.bookingservice.service.exception.ValidationException;
import com.epam.bookingservice.service.validator.Validator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String PASSWORD = "password";
    private static final String ENCODED_PASSWORD = "encoded_password";
    private static final String INCORRECT_PASSWORD = "incorrect_password";
    private static final String INCORRECT_ENCODED_PASSWORD = "incorrect_encoded_password";
    private static final String USER_EMAIL = "user@email.com";
    private static final String INCORRECT_USER_EMAIL = "incorrrect_user@email.com";

    private static final User USER = initUser();
    private static final UserEntity USER_ENTITY = initUserEntity();

    private static User initUser() {
        return User.builder()
                .setEmail(USER_EMAIL)
                .setPassword(ENCODED_PASSWORD)
                .build();
    }

    private static UserEntity initUserEntity() {
        return UserEntity.builder()
                .setEmail(USER_EMAIL)
                .setPassword(ENCODED_PASSWORD)
                .build();
    }

    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncryptor passwordEncryptor;

    @Mock
    private Validator<User> userValidator;

    @Mock
    private Validator<String> emailValidator;

    private Mapper<UserEntity, User> userMapper = new UserMapper(new RoleMapper());

    private UserServiceImpl userService;

    @Before
    public void injectMocks() {
        userService = new UserServiceImpl(userDao, userValidator, emailValidator, passwordEncryptor, userMapper);
    }

    @After
    public void resetMocks() {
        Mockito.reset(userDao, passwordEncryptor, userValidator, emailValidator);
    }

    @Test
    public void userShouldLoginSuccessfully() {
        when(passwordEncryptor.encrypt(eq(PASSWORD), anyString()))
                .thenReturn(ENCODED_PASSWORD);
        when(userDao.findByEmail(eq(USER_EMAIL)))
                .thenReturn(Optional.of(USER_ENTITY));

        Optional<User> user = userService.login(USER_EMAIL, PASSWORD);

        assertEquals(USER, user.orElseThrow(() -> new RuntimeException("Login failed")));
        verify(passwordEncryptor, times(1)).encrypt(eq(PASSWORD), anyString());
        verifyZeroInteractions(userValidator);
        verify(emailValidator, times(1)).validate(anyString());
    }

    @Test
    public void userShouldNotLoginInCaseOfIncorrectPassword() {
        when(passwordEncryptor.encrypt(eq(INCORRECT_PASSWORD), anyString()))
                .thenReturn(INCORRECT_ENCODED_PASSWORD);
        when(userDao.findByEmail(eq(USER_EMAIL)))
                .thenReturn(Optional.of(USER_ENTITY));

        Optional<User> user = userService.login(USER_EMAIL, INCORRECT_PASSWORD);

        assertFalse(user.isPresent());
        verify(passwordEncryptor, times(1)).encrypt(eq(INCORRECT_PASSWORD), anyString());
        verifyZeroInteractions(userValidator);
    }

    @Test
    public void userShouldNotLoginInCaseOfIncorrectEmail() {
        when(passwordEncryptor.encrypt(eq(PASSWORD), anyString()))
                .thenReturn(ENCODED_PASSWORD);
        when(userDao.findByEmail(eq(INCORRECT_USER_EMAIL)))
                .thenReturn(Optional.empty());

        Optional<User> user = userService.login(INCORRECT_USER_EMAIL, PASSWORD);

        assertFalse(user.isPresent());
        verifyZeroInteractions(passwordEncryptor);
        verifyZeroInteractions(userValidator);
    }

    @Test
    public void userShouldRegister() {
        doNothing().when(userValidator).validate(any(User.class));
        when(passwordEncryptor.encrypt(eq(PASSWORD), anyString()))
                .thenReturn(ENCODED_PASSWORD);
        when(userDao.findByEmail(eq(USER_EMAIL)))
                .thenReturn(Optional.empty());
        when(userDao.save(any(UserEntity.class))).then(returnsFirstArg());

        User userToBeRegistered = User.builder()
                .setPassword(PASSWORD)
                .setEmail(USER_EMAIL)
                .build();

        User registeredUser = userService.register(userToBeRegistered);

        assertEquals(USER, registeredUser);
        verify(userValidator, times(1)).validate(eq(userToBeRegistered));
        verify(userDao, times(1)).findByEmail(eq(USER_EMAIL));
        verify(userDao, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void userShouldNotRegisterWithInvalidPasswordOrEmail() {
        doThrow(ValidationException.class).when(userValidator).validate(any(User.class));

        assertThrows(ValidationException.class, () -> userService.register(USER));
    }

    @Test
    public void userShouldNotRegisterAsEmailIsAlreadyUsed() {
        doNothing().when(userValidator).validate(any(User.class));
        when(userDao.findByEmail(eq(USER_EMAIL))).thenReturn(Optional.of(USER_ENTITY));

        assertThrows(UserAlreadyExistsException.class, () -> userService.register(USER));
    }
}