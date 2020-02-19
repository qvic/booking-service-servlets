package com.salon.booking.service.impl;

import com.salon.booking.dao.UserDao;
import com.salon.booking.domain.Role;
import com.salon.booking.domain.User;
import com.salon.booking.domain.UserLoginForm;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;
import com.salon.booking.entity.RoleEntity;
import com.salon.booking.entity.UserEntity;
import com.salon.booking.mapper.Mapper;
import com.salon.booking.service.UserService;
import com.salon.booking.service.encoder.PasswordEncoder;
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

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String NAME = "name";
    private static final String ENCODED_PASSWORD = "encoded_password";
    private static final String USER_EMAIL = "user@email.com";

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
    private Validator<String> emailValidator;

    @Mock
    private Mapper<UserEntity, User> userMapper;

    private UserService userService;

    @Before
    public void injectMocks() {
        userService = new UserServiceImpl(userDao, userMapper);
    }

    @After
    public void resetMocks() {
        Mockito.reset(userDao, passwordEncoder, userValidator, userLoginFormValidator, emailValidator);
    }

    @Test
    public void findAllWorkersShouldReturnMappedWorkers() {
        UserEntity workerEntity = UserEntity.builder(USER_ENTITY).setRole(RoleEntity.WORKER).build();
        User worker = User.builder(USER).setRole(Role.WORKER).build();

        when(userMapper.mapEntityToDomain(eq(workerEntity))).thenReturn(worker);

        PageProperties properties = new PageProperties(0, 1);
        Page<UserEntity> workersPage = new Page<>(Collections.singletonList(workerEntity),
                properties, 1);
        when(userDao.findAllWorkers(eq(properties))).thenReturn(workersPage);

        Page<User> workers = userService.findAllWorkers(properties);

        assertThat(Collections.singletonList(worker), is(workers.getItems()));
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

        assertThat(page.getItems(), is(mappedLastPage.getItems()));
        assertEquals(page.getProperties(), mappedLastPage.getProperties());
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