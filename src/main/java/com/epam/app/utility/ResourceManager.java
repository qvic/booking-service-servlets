package com.epam.app.utility;

import java.util.Locale;
import java.util.ResourceBundle;

import static com.epam.app.utility.Config.L10N_RESOURCE_BUNDLE_NAME;

public class ResourceManager {

    private ResourceBundle resourceBundle;

    public ResourceManager() {
        resourceBundle = ResourceBundle.getBundle(L10N_RESOURCE_BUNDLE_NAME, Locale.getDefault());
    }

    public void changeLocale(Locale locale) {
        resourceBundle = ResourceBundle.getBundle(L10N_RESOURCE_BUNDLE_NAME, locale);
    }

    public String getString(String key) {
        return resourceBundle.getString(key);
    }
}