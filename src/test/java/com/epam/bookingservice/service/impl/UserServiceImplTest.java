package com.epam.bookingservice.service.impl;

import com.epam.bookingservice.dao.UserDao;
import com.epam.bookingservice.domain.Role;
import com.epam.bookingservice.domain.User;
import com.epam.bookingservice.domain.page.Page;
import com.epam.bookingservice.domain.page.PageProperties;
import com.epam.bookingservice.entity.RoleEntity;
import com.epam.bookingservice.entity.UserEntity;
import com.epam.bookingservice.mapper.Mapper;
import com.epam.bookingservice.service.encoder.PasswordEncoder;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String NAME = "name";
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
                .setName(NAME)
                .setEmail(USER_EMAIL)
                .setPassword(ENCODED_PASSWORD)
                .setRole(Role.CLIENT)
                .build();
    }

    private static UserEntity initUserEntity() {
        return UserEntity.builder()
                .setName(NAME)
                .setEmail(USER_EMAIL)
                .setPassword(ENCODED_PASSWORD)
                .setRole(RoleEntity.CLIENT)
                .build();
    }

    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Validator<User> userValidator;

    @Mock
    private Validator<String> emailValidator;

    @Mock
    private Mapper<UserEntity, User> userMapper;

    private UserServiceImpl userService;

    @Before
    public void injectMocks() {
        userService = new UserServiceImpl(userDao, userValidator, emailValidator, passwordEncoder, userMapper);
    }

    @After
    public void resetMocks() {
        Mockito.reset(userDao, passwordEncoder, userValidator, emailValidator);
    }

    @Test
    public void userShouldLoginSuccessfully() {
        when(passwordEncoder.verify(eq(PASSWORD), eq(ENCODED_PASSWORD)))
                .thenReturn(true);
        when(userDao.findByEmail(eq(USER_EMAIL)))
                .thenReturn(Optional.of(USER_ENTITY));
        when(userMapper.mapEntityToDomain(eq(USER_ENTITY))).thenReturn(USER);

        Optional<User> user = userService.login(USER_EMAIL, PASSWORD);

        assertEquals(USER, user.orElseThrow(() -> new RuntimeException("Login failed")));
        verify(passwordEncoder).verify(anyString(), anyString());
        verifyZeroInteractions(userValidator);
        verify(emailValidator).validate(anyString());
    }

    @Test
    public void userShouldNotLoginInCaseOfIncorrectPassword() {
        when(passwordEncoder.verify(eq(INCORRECT_PASSWORD), eq(ENCODED_PASSWORD)))
                .thenReturn(false);
        when(userDao.findByEmail(eq(USER_EMAIL)))
                .thenReturn(Optional.of(USER_ENTITY));

        Optional<User> user = userService.login(USER_EMAIL, INCORRECT_PASSWORD);

        assertFalse(user.isPresent());
        verify(passwordEncoder).verify(anyString(), anyString());
        verifyZeroInteractions(userValidator);
    }

    @Test
    public void userShouldNotLoginInCaseOfIncorrectEmail() {
        when(passwordEncoder.encode(eq(PASSWORD)))
                .thenReturn(ENCODED_PASSWORD);
        when(userDao.findByEmail(eq(INCORRECT_USER_EMAIL)))
                .thenReturn(Optional.empty());

        Optional<User> user = userService.login(INCORRECT_USER_EMAIL, PASSWORD);

        assertFalse(user.isPresent());
        verifyZeroInteractions(passwordEncoder);
        verifyZeroInteractions(userValidator);
    }

    @Test
    public void userShouldRegister() {
        doNothing().when(userValidator).validate(any(User.class));
        when(passwordEncoder.encode(eq(PASSWORD)))
                .thenReturn(ENCODED_PASSWORD);
        when(userDao.findByEmail(eq(USER_EMAIL)))
                .thenReturn(Optional.empty());
        when(userDao.save(any(UserEntity.class))).then(returnsFirstArg());
        when(userMapper.mapDomainToEntity(eq(USER))).thenReturn(USER_ENTITY);
        when(userMapper.mapEntityToDomain(eq(USER_ENTITY))).thenReturn(USER);

        User userToBeRegistered = User.builder()
                .setName(NAME)
                .setPassword(PASSWORD)
                .setEmail(USER_EMAIL)
                .build();

        User registeredUser = userService.register(userToBeRegistered);

        assertEquals(USER, registeredUser);
        verify(userValidator).validate(eq(userToBeRegistered));
        verify(userDao).findByEmail(eq(USER_EMAIL));
        verify(userDao).save(any(UserEntity.class));
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

    @Test
    public void findAllWorkersShouldReturnMappedWorkers() {
        UserEntity workerEntity = UserEntity.builder(USER_ENTITY).setRole(RoleEntity.WORKER).build();
        User worker = User.builder(USER).setRole(Role.WORKER).build();

        when(userMapper.mapEntityToDomain(eq(workerEntity))).thenReturn(worker);
        when(userDao.findAllWorkers()).thenReturn(Collections.singletonList(workerEntity));

        List<User> workers = userService.findAllWorkers();

        assertEquals(Collections.singletonList(worker), workers);
    }

    @Test
    public void findAllShouldReturnLastPageIfPageNumberIsTooBig() {
        PageProperties requestedProperties = new PageProperties(2, 1);
        Page<UserEntity> daoPage = new Page<>(Collections.emptyList(), requestedProperties, 2);
        Page<UserEntity> lastPage = new Page<>(Collections.singletonList(USER_ENTITY), new PageProperties(1, 1), 2);
        Page<User> mappedLastPage = new Page<>(Collections.singletonList(USER), new PageProperties(1, 1), 2);

        when(userMapper.mapEntityToDomain(eq(USER_ENTITY))).thenReturn(USER);
        when(userDao.findAll(eq(requestedProperties))).thenReturn(daoPage);
        when(userDao.findAll(eq(lastPage.getProperties()))).thenReturn(lastPage);

        Page<User> page = userService.findAll(requestedProperties);

        assertArrayEquals(page.getItems().toArray(), mappedLastPage.getItems().toArray());
        assertEquals(page.getProperties(), mappedLastPage.getProperties());
    }
}