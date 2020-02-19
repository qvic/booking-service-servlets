package com.salon.booking.mapper;

import com.salon.booking.domain.Role;
import com.salon.booking.domain.User;
import com.salon.booking.entity.RoleEntity;
import com.salon.booking.entity.UserEntity;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {
    
    @Mock
    private Mapper<RoleEntity, Role> roleMapper;

    @InjectMocks
    private UserMapper userMapper;

    @After
    public void resetMocks() {
        reset(roleMapper);
    }

    @Test
    public void mapDomainToEntityShouldMapCorrectly() {
        when(roleMapper.mapDomainToEntity(eq(Role.CLIENT))).thenReturn(RoleEntity.CLIENT);

        User user = User.builder()
                .setPassword("password")
                .setEmail("email")
                .setName("name")
                .setId(1)
                .setRole(Role.CLIENT)
                .build();

        UserEntity expectedUserEntity = UserEntity.builder()
                .setPassword("password")
                .setEmail("email")
                .setName("name")
                .setId(1)
                .setRole(RoleEntity.CLIENT)
                .build();

        assertEquals(expectedUserEntity, userMapper.mapDomainToEntity(user));
    }

    @Test
    public void mapEntityToDomainShouldMapCorrectly() {
        when(roleMapper.mapEntityToDomain(eq(RoleEntity.CLIENT))).thenReturn(Role.CLIENT);

        UserEntity userEntity = UserEntity.builder()
                .setPassword("password")
                .setEmail("email")
                .setName("name")
                .setId(1)
                .setRole(RoleEntity.CLIENT)
                .build();

        User expectedUser = User.builder()
                .setPassword("password")
                .setEmail("email")
                .setName("name")
                .setId(1)
                .setRole(Role.CLIENT)
                .build();

        assertEquals(expectedUser, userMapper.mapEntityToDomain(userEntity));
    }

    @Test
    public void mapDomainToEntityShouldReturnNullOnNullParameter() {
        assertNull(userMapper.mapDomainToEntity(null));
    }

    @Test
    public void mapEntityToDomainShouldReturnNullOnNullParameter() {
        assertNull(userMapper.mapEntityToDomain(null));
    }
}