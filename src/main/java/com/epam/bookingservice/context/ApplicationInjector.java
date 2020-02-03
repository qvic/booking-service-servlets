package com.epam.bookingservice.context;

import com.epam.bookingservice.command.Command;
import com.epam.bookingservice.command.user.IndexCommand;
import com.epam.bookingservice.command.user.ListCommand;
import com.epam.bookingservice.command.user.LoginCommand;
import com.epam.bookingservice.command.user.LogoutCommand;
import com.epam.bookingservice.command.user.RegisterCommand;
import com.epam.bookingservice.dao.FeedbackDao;
import com.epam.bookingservice.dao.ServiceDao;
import com.epam.bookingservice.dao.UserDao;
import com.epam.bookingservice.dao.impl.FeedbackDaoImpl;
import com.epam.bookingservice.dao.impl.ServiceDaoImpl;
import com.epam.bookingservice.dao.impl.UserDaoImpl;
import com.epam.bookingservice.entity.User;
import com.epam.bookingservice.service.PasswordEncryptor;
import com.epam.bookingservice.service.UserService;
import com.epam.bookingservice.service.impl.UserServiceImpl;
import com.epam.bookingservice.service.validator.UserValidator;
import com.epam.bookingservice.service.validator.Validator;
import com.epam.bookingservice.utility.Config;
import com.epam.bookingservice.utility.DatabaseConnector;
import com.epam.bookingservice.utility.HikariDatabaseConnector;

import java.util.HashMap;
import java.util.Map;

public class ApplicationInjector {

    private static final ApplicationInjector INSTANCE = new ApplicationInjector();

    private static final DatabaseConnector DATABASE_CONNECTOR = new HikariDatabaseConnector(Config.DB_SETTINGS_BUNDLE_NAME);

    private static final PasswordEncryptor PASSWORD_ENCRYPTOR = new PasswordEncryptor();
    private static final UserDao USER_DAO = new UserDaoImpl(DATABASE_CONNECTOR);
    private static final Validator<User> USER_VALIDATOR = new UserValidator();

    private static final UserService USER_SERVICE = new UserServiceImpl(USER_DAO, USER_VALIDATOR, PASSWORD_ENCRYPTOR);

    private static final Map<String, Command> COMMANDS = initializeCommands();
    private static final Command DEFAULT_COMMAND = new IndexCommand();

    private ApplicationInjector() {
    }

    public static ApplicationInjector getInstance() {
        return INSTANCE;
    }

    public UserService getUserService() {
        return USER_SERVICE;
    }

    public Map<String, Command> getCommands() {
        return COMMANDS;
    }

    public Command getDefaultCommand() {
        return DEFAULT_COMMAND;
    }

    private static Map<String, Command> initializeCommands() {
        Map<String, Command> commands = new HashMap<>();

        commands.put("/app/login", new LoginCommand(USER_SERVICE));
        commands.put("/app/logout", new LogoutCommand());
        commands.put("/app/signup", new RegisterCommand(USER_SERVICE));
        commands.put("/app/list", new ListCommand(USER_SERVICE));

        return commands;
    }
}