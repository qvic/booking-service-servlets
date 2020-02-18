package com.bookingservice.domain;

import com.bookingservice.domain.page.PageProperties;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PagePropertiesTest {

    @Test
    public void pageNumberShouldBeValidatedAfterCreation() {
        PageProperties pageProperties = new PageProperties(-10, 2);

        assertEquals(0, pageProperties.getPageNumber());
        assertEquals(2, pageProperties.getItemsPerPage());
    }

    @Test
    public void itemsPerPageShouldBeValidatedAfterCreation() {
        PageProperties pageProperties = new PageProperties(1, -10);

        assertEquals(1, pageProperties.getPageNumber());
        assertEquals(0, pageProperties.getItemsPerPage());
    }

    @Test
    public void pagePropertiesShouldReturnZeroOffsetOnFirstPage() {
        PageProperties pageProperties = new PageProperties(0, 10);

        assertEquals(0, pageProperties.getOffset());
    }

    @Test
    public void pagePropertiesShouldReturnValidOffset() {
        PageProperties pageProperties = new PageProperties(5, 10);

        assertEquals(50, pageProperties.getOffset());
    }

    @Test
    public void pagePropertiesShouldBuildUsingDefaultValuesIfParametersAreInvalid() {
        PageProperties pageProperties = PageProperties.buildByParameters("abc", "", 10);

        assertEquals(0, pageProperties.getPageNumber());
        assertEquals(10, pageProperties.getItemsPerPage());
    }

    @Test
    public void pagePropertiesShouldBuildCorrectly() {
        PageProperties pageProperties = PageProperties.buildByParameters("123", "30", 10);

        assertEquals(123, pageProperties.getPageNumber());
        assertEquals(30, pageProperties.getItemsPerPage());
    }

    @Test
    public void toStringShouldReturnCorrectInformation() {
        PageProperties pageProperties = new PageProperties(2, 10);

        assertEquals("Page 2 (20 - 29)", pageProperties.toString());
    }
}