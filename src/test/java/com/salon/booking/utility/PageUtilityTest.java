package com.salon.booking.utility;

import org.junit.Test;

import static org.junit.Assert.*;

public class PageUtilityTest {

    @Test
    public void getViewPathByNameShouldReturnCorrectViewPath() {
        assertEquals("/WEB-INF/views/test.jsp", PageUtility.getViewPathByName("test"));
    }
}