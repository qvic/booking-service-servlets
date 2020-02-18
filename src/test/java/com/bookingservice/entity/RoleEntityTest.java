package com.bookingservice.entity;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class RoleEntityTest {

    @Test
    public void findByNameShouldReturnCorrectEnumValue() {
        assertThat(RoleEntity.findByName("CLIENT"), equalTo(Optional.of(RoleEntity.CLIENT)));
        assertThat(RoleEntity.findByName("WORKER"), equalTo(Optional.of(RoleEntity.WORKER)));
        assertThat(RoleEntity.findByName("ADMIN"), equalTo(Optional.of(RoleEntity.ADMIN)));
    }

    @Test
    public void findByNameShouldReturnEmptyOptionalIfEnumValueNotFound() {
        assertThat(RoleEntity.findByName("USER"), equalTo(Optional.empty()));
    }

    @Test
    public void findByNameShouldReturnEmptyOptionalIfNameIsNull() {
        assertThat(RoleEntity.findByName(null), equalTo(Optional.empty()));
    }
}