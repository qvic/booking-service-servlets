package com.salon.booking.utility;

public final class PageUtility {

    private PageUtility() {

    }

    public static String getViewPathByName(String name) {
        return String.format("/WEB-INF/views/%s.jsp", name);
    }
}
