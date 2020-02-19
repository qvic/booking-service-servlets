package com.salon.booking.service.impl;

import com.salon.booking.dao.UserDao;
import com.salon.booking.domain.Role;
import com.salon.booking.domain.User;
import com.salon.booking.domain.UserLoginForm;
import com.salon.booking.entity.RoleEntity;
import com.salon.booking.entity.UserEntity;
import com.salon.booking.mapper.Mapper;
import com.salon.booking.service.encoder.PasswordEncoder;
import com.salon.booking.service.exception.UserAlreadyExistsException;
import com.salon.booking.service.exception.ValidationException;
import com.salon.booking.service.validator.Validator;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
public class AuthServiceImplTest {

    private static final String NAME = "name";
    private static final String PASSWORD = "password";
    private static final String ENCODED_PASSWORD = "encoded_password";
    private static final String INCORRECT_PASSWORD = "incorrect_password";
    private static final String USER_EMAIL = "user@email.com";
    private static final String INCORRECT_USER_EMAIL = "incorrrect_user@email.com";

    private static final User USER = initUser();
    private static final UserEntity USER_ENTITY = initUserEntity();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Validator<User> userValidator;

    @Mock
    private Validator<UserLoginForm> userLoginFormValidator;

    @Mock
    private Mapper<UserEntity, User> userMapper;

    private AuthServiceImpl authService;

    @Before
    public void injectMocks() {
        authService = new AuthServiceImpl(userDao, userValidator, userLoginFormValidator, passwordEncoder, userMapper);
    }

    @After
    public void resetMocks() {
        Mockito.reset(userDao, passwordEncoder, userValidator, userLoginFormValidator);
    }

    @Test
    public void userShouldLoginSuccessfully() {
        when(passwordEncoder.verify(eq(PASSWORD), eq(ENCODED_PASSWORD)))
                .thenReturn(true);
        when(userDao.findByEmail(eq(USER_EMAIL)))
                .thenReturn(Optional.of(USER_ENTITY));
        when(userMapper.mapEntityToDomain(eq(USER_ENTITY))).thenReturn(USER);

        Optional<User> user = authService.login(new UserLoginForm(USER_EMAIL, PASSWORD));

        assertEquals(USER, user.orElseThrow(() -> new RuntimeException("Login failed")));
        verify(passwordEncoder).verify(anyString(), anyString());
        verify(userLoginFormValidator).validate(any());
    }

    @Test
    public void userShouldNotLoginInCaseOfIncorrectPassword() {
        when(passwordEncoder.verify(eq(INCORRECT_PASSWORD), eq(ENCODED_PASSWORD)))
                .thenReturn(false);
        when(userDao.findByEmail(eq(USER_EMAIL)))
                .thenReturn(Optional.of(USER_ENTITY));

        Optional<User> user = authService.login(new UserLoginForm(USER_EMAIL, INCORRECT_PASSWORD));

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

        Optional<User> user = authService.login(new UserLoginForm(INCORRECT_USER_EMAIL, PASSWORD));

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

        User registeredUser = authService.register(userToBeRegistered);

        assertEquals(USER, registeredUser);
        verify(userValidator).validate(eq(userToBeRegistered));
        verify(userDao).findByEmail(eq(USER_EMAIL));
        verify(userDao).save(any(UserEntity.class));
    }

    @Test
    public void userShouldNotRegisterWithInvalidPasswordOrEmail() {
        doThrow(ValidationException.class).when(userValidator).validate(any(User.class));

        expectedException.expect(ValidationException.class);
        authService.register(USER);
    }

    @Test
    public void userShouldNotRegisterAsEmailIsAlreadyUsed() {
        doNothing().when(userValidator).validate(any(User.class));
        when(userDao.findByEmail(eq(USER_EMAIL))).thenReturn(Optional.of(USER_ENTITY));

        expectedException.expect(UserAlreadyExistsException.class);
        authService.register(USER);
    }

    private static UserEntity initUserEntity() {
        return UserEntity.builder()
                .setName(NAME)
                .setEmail(USER_EMAIL)
                .setPassword(ENCODED_PASSWORD)
                .setRole(RoleEntity.CLIENT)
                .build();
    }

    private static User initUser() {
        return User.builder()
                .setName(NAME)
                .setEmail(USER_EMAIL)
                .setPassword(ENCODED_PASSWORD)
                .setRole(Role.CLIENT)
                .build();
    }
}