package com.epam.app.injector;

import com.epam.app.domain.User;
import com.epam.app.repository.UserRepository;
import com.epam.app.repository.impl.UserRepositoryImpl;
import com.epam.app.service.PasswordEncryptor;
import com.epam.app.service.UserService;
import com.epam.app.service.impl.UserServiceImpl;
import com.epam.app.service.validator.UserValidator;
import com.epam.app.service.validator.Validator;
import com.epam.app.utility.ResourceManager;

public class ApplicationInjector {

    private static final ApplicationInjector INSTANCE = new ApplicationInjector();

    private static final Validator<User> USER_VALIDATOR = new UserValidator();

    private static final PasswordEncryptor PASSWORD_ENCRYPTOR = new PasswordEncryptor();

    private static final UserRepository USER_REPOSITORY = new UserRepositoryImpl();

    private static final UserService USER_SERVICE = new UserServiceImpl(
            USER_REPOSITORY, USER_VALIDATOR, PASSWORD_ENCRYPTOR);

    private static final ResourceManager RESOURCE_MANAGER = new ResourceManager();

    public static ApplicationInjector getInstance() {
        return INSTANCE;
    }

    public UserService getUserService() {
        return USER_SERVICE;
    }

    private ApplicationInjector() {
    }
}