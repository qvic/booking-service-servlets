package com.bookingservice.domain;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RoleTest {

    @Test
    public void findByNameShouldReturnCorrectEnumValue() {
        assertThat(Role.findByName("CLIENT"), equalTo(Optional.of(Role.CLIENT)));
        assertThat(Role.findByName("WORKER"), equalTo(Optional.of(Role.WORKER)));
        assertThat(Role.findByName("ADMIN"), equalTo(Optional.of(Role.ADMIN)));
    }

    @Test
    public void findByNameShouldReturnEmptyOptionalIfEnumValueNotFound() {
        assertThat(Role.findByName("USER"), equalTo(Optional.empty()));
    }

    @Test
    public void findByNameShouldReturnEmptyOptionalIfNameIsNull() {
        assertThat(Role.findByName(null), equalTo(Optional.empty()));
    }
}