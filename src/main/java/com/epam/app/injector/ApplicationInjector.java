package com.epam.app.injector;

import com.epam.app.dao.impl.UserDaoImpl;
import com.epam.app.domain.User;
import com.epam.app.dao.UserDao;
import com.epam.app.service.PasswordEncryptor;
import com.epam.app.service.UserService;
import com.epam.app.service.impl.UserServiceImpl;
import com.epam.app.service.validator.UserValidator;
import com.epam.app.service.validator.Validator;
import com.epam.app.utility.Config;
import com.epam.app.utility.DatabaseConnector;
import com.epam.app.utility.ResourceManager;

public class ApplicationInjector {

    private static final ApplicationInjector INSTANCE = new ApplicationInjector();

    private static final Validator<User> USER_VALIDATOR = new UserValidator();

    private static final PasswordEncryptor PASSWORD_ENCRYPTOR = new PasswordEncryptor();

    private static final DatabaseConnector DATABASE_CONNECTOR = new DatabaseConnector(Config.DB_SETTINGS_BUNDLE_NAME);

    private static final UserDao USER_DAO = new UserDaoImpl(DATABASE_CONNECTOR);

    private static final UserService USER_SERVICE = new UserServiceImpl(
            USER_DAO, USER_VALIDATOR, PASSWORD_ENCRYPTOR);

    private static final ResourceManager RESOURCE_MANAGER = new ResourceManager();

    public static ApplicationInjector getInstance() {
        return INSTANCE;
    }

    public UserService getUserService() {
        return USER_SERVICE;
    }

    public UserDao getUserDao() {
        return USER_DAO;
    }

    private ApplicationInjector() {
    }
}