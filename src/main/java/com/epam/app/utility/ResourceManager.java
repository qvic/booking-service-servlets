package com.epam.app.utility;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceManager {

    private static final String RESOURCE_BUNDLE_NAME = "text";

    private ResourceBundle resourceBundle;

    public ResourceManager() {
        resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, Locale.getDefault());
    }

    public void changeLocale(Locale locale) {
        resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, locale);
    }

    public String getString(String key) {
        return resourceBundle.getString(key);
    }
}