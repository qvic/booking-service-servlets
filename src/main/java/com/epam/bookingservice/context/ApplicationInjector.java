package com.epam.bookingservice.context;

import com.epam.bookingservice.command.Command;
import com.epam.bookingservice.command.HomeCommand;
import com.epam.bookingservice.command.client.CreateOrderCommand;
import com.epam.bookingservice.command.client.ShowOrdersCommand;
import com.epam.bookingservice.command.timetable.ShowTimetablesCommand;
import com.epam.bookingservice.command.user.LoginCommand;
import com.epam.bookingservice.command.user.LogoutCommand;
import com.epam.bookingservice.command.user.RegisterCommand;
import com.epam.bookingservice.command.user.ShowUsersCommand;
import com.epam.bookingservice.dao.OrderDao;
import com.epam.bookingservice.dao.TimeslotDao;
import com.epam.bookingservice.dao.UserDao;
import com.epam.bookingservice.dao.impl.OrderDaoImpl;
import com.epam.bookingservice.dao.impl.TimeslotDaoImpl;
import com.epam.bookingservice.dao.impl.UserDaoImpl;
import com.epam.bookingservice.dao.impl.connector.DataSourceConnector;
import com.epam.bookingservice.dao.impl.connector.HikariDataSourceConnector;
import com.epam.bookingservice.dao.impl.connector.TransactionManager;
import com.epam.bookingservice.dao.impl.connector.TransactionManagerImpl;
import com.epam.bookingservice.domain.Order;
import com.epam.bookingservice.domain.Service;
import com.epam.bookingservice.domain.Timeslot;
import com.epam.bookingservice.domain.User;
import com.epam.bookingservice.entity.OrderEntity;
import com.epam.bookingservice.entity.ServiceEntity;
import com.epam.bookingservice.entity.TimeslotEntity;
import com.epam.bookingservice.entity.UserEntity;
import com.epam.bookingservice.mapper.Mapper;
import com.epam.bookingservice.mapper.OrderMapper;
import com.epam.bookingservice.mapper.ServiceMapper;
import com.epam.bookingservice.mapper.TimeslotMapper;
import com.epam.bookingservice.mapper.UserMapper;
import com.epam.bookingservice.service.OrderService;
import com.epam.bookingservice.service.OrderServiceImpl;
import com.epam.bookingservice.service.PasswordEncryptor;
import com.epam.bookingservice.service.TimeslotService;
import com.epam.bookingservice.service.UserService;
import com.epam.bookingservice.service.impl.TimeslotServiceImpl;
import com.epam.bookingservice.service.impl.UserServiceImpl;
import com.epam.bookingservice.service.validator.EmailValidator;
import com.epam.bookingservice.service.validator.UserValidator;
import com.epam.bookingservice.service.validator.Validator;

import java.util.HashMap;
import java.util.Map;

public final class ApplicationInjector {

    private static final ApplicationInjector INSTANCE = new ApplicationInjector();

    private static final TransactionManager TRANSACTION_MANAGER = new TransactionManagerImpl();

    private static final DataSourceConnector DATABASE_CONNECTOR = new HikariDataSourceConnector("db", TRANSACTION_MANAGER);

    private static final PasswordEncryptor PASSWORD_ENCRYPTOR = new PasswordEncryptor();
    private static final Validator<String> EMAIL_VALIDATOR = new EmailValidator();
    private static final Validator<User> USER_VALIDATOR = new UserValidator();

    private static final UserDao USER_DAO = new UserDaoImpl(DATABASE_CONNECTOR);
    private static final TimeslotDao TIMESLOT_DAO = new TimeslotDaoImpl(DATABASE_CONNECTOR);
    private static final OrderDao ORDER_DAO = new OrderDaoImpl(DATABASE_CONNECTOR);

    private static final Mapper<UserEntity, User> USER_MAPPER = new UserMapper();
    private static final Mapper<ServiceEntity, Service> SERVICE_MAPPER = new ServiceMapper();
    private static final Mapper<OrderEntity, Order> ORDER_MAPPER = new OrderMapper(USER_MAPPER, SERVICE_MAPPER);
    private static final Mapper<TimeslotEntity, Timeslot> TIMESLOT_MAPPER = new TimeslotMapper(ORDER_MAPPER);

    private static final UserService USER_SERVICE = new UserServiceImpl(USER_DAO, USER_VALIDATOR, EMAIL_VALIDATOR, PASSWORD_ENCRYPTOR);
    private static final TimeslotService TIMESLOT_SERVICE = new TimeslotServiceImpl(TIMESLOT_DAO, ORDER_DAO, TIMESLOT_MAPPER, TRANSACTION_MANAGER);
    private static final OrderService ORDER_SERVICE = new OrderServiceImpl(ORDER_DAO, ORDER_MAPPER);

    private static final Map<String, Command> ROUTE_TO_COMMAND = initializeCommands();

    private ApplicationInjector() {
    }

    public Map<String, Command> getCommands() {
        return ROUTE_TO_COMMAND;
    }

    private static Map<String, Command> initializeCommands() {
        Map<String, Command> commands = new HashMap<>();

        commands.put("/", new HomeCommand());
        commands.put("/app/login", new LoginCommand(USER_SERVICE));
        commands.put("/app/logout", new LogoutCommand());
        commands.put("/app/signup", new RegisterCommand(USER_SERVICE));
        commands.put("/app/users", new ShowUsersCommand(USER_SERVICE));

        commands.put("/app/timetables", new ShowTimetablesCommand(TIMESLOT_SERVICE));
        commands.put("/app/client/orders", new ShowOrdersCommand(ORDER_SERVICE));
        commands.put("/app/client/create-order", new CreateOrderCommand(TIMESLOT_SERVICE));

        return commands;
    }

    public static ApplicationInjector getInstance() {
        return INSTANCE;
    }
}