package com.epam.bookingservice.utility;

public class PageUtility {

    private PageUtility() {

    }

    public static String getViewByName(String name) {
        return String.format("/WEB-INF/views/%s.jsp", name);
    }
}
