package com.bookingservice.context;

import com.bookingservice.command.Command;
import com.bookingservice.command.HomeCommand;
import com.bookingservice.command.admin.ShowAdminTimetableCommand;
import com.bookingservice.command.admin.ShowUnapprovedFeedbackByPagesCommand;
import com.bookingservice.command.admin.ShowUsersByPagesCommand;
import com.bookingservice.command.client.CreateOrderCommand;
import com.bookingservice.command.client.LeaveFeedbackCommand;
import com.bookingservice.command.client.SelectServiceCommand;
import com.bookingservice.command.client.SelectTimeslotCommand;
import com.bookingservice.command.client.SelectWorkerCommand;
import com.bookingservice.command.client.ShowClientFeedbackByPagesCommand;
import com.bookingservice.command.client.ShowOrdersCommand;
import com.bookingservice.command.user.LoginCommand;
import com.bookingservice.command.user.LogoutCommand;
import com.bookingservice.command.user.RegisterCommand;
import com.bookingservice.command.worker.ShowWorkerFeedbackByPagesCommand;
import com.bookingservice.command.worker.ShowWorkerTimetableCommand;
import com.bookingservice.dao.OrderDao;
import com.bookingservice.dao.ServiceDao;
import com.bookingservice.dao.TimeslotDao;
import com.bookingservice.dao.UserDao;
import com.bookingservice.dao.impl.OrderDaoImpl;
import com.bookingservice.dao.impl.ServiceDaoImpl;
import com.bookingservice.dao.impl.TimeslotDaoImpl;
import com.bookingservice.dao.impl.connector.DataSourceConnector;
import com.bookingservice.dao.impl.connector.DataSourceTransactionManager;
import com.bookingservice.dao.impl.connector.HikariDataSourceConnector;
import com.bookingservice.domain.Order;
import com.bookingservice.domain.Role;
import com.bookingservice.domain.Service;
import com.bookingservice.domain.Timeslot;
import com.bookingservice.domain.User;
import com.bookingservice.domain.UserLoginForm;
import com.bookingservice.entity.OrderEntity;
import com.bookingservice.entity.RoleEntity;
import com.bookingservice.entity.ServiceEntity;
import com.bookingservice.entity.TimeslotEntity;
import com.bookingservice.entity.UserEntity;
import com.bookingservice.mapper.Mapper;
import com.bookingservice.mapper.OrderMapper;
import com.bookingservice.mapper.RoleMapper;
import com.bookingservice.mapper.ServiceMapper;
import com.bookingservice.mapper.TimeslotMapper;
import com.bookingservice.mapper.UserMapper;
import com.bookingservice.service.FeedbackService;
import com.bookingservice.service.OrderService;
import com.bookingservice.service.TimeslotService;
import com.bookingservice.service.UserService;
import com.bookingservice.service.encoder.PasswordEncoder;
import com.bookingservice.service.impl.OrderServiceImpl;
import com.bookingservice.service.impl.TimeslotServiceImpl;
import com.bookingservice.service.impl.AuthServiceImpl;
import com.bookingservice.service.impl.UserServiceImpl;
import com.bookingservice.service.validator.EmailValidator;
import com.bookingservice.dao.impl.UserDaoImpl;
import com.bookingservice.service.AuthService;
import com.bookingservice.service.validator.PasswordValidator;
import com.bookingservice.service.validator.UserLoginFormValidator;
import com.bookingservice.service.validator.UserValidator;
import com.bookingservice.service.validator.Validator;

import java.util.HashMap;
import java.util.Map;

public final class ApplicationInjector {

    private static final ApplicationInjector INSTANCE = new ApplicationInjector();

    private static final DataSourceTransactionManager TRANSACTION_MANAGER = new DataSourceTransactionManager();

    private static final DataSourceConnector DATABASE_CONNECTOR = new HikariDataSourceConnector("db", TRANSACTION_MANAGER);

    private static final PasswordEncoder PASSWORD_ENCRYPTOR = new PasswordEncoder();
    private static final Validator<String> PASSWORD_VALIDATOR = new PasswordValidator();
    private static final Validator<String> EMAIL_VALIDATOR = new EmailValidator();
    private static final Validator<User> USER_VALIDATOR = new UserValidator(EMAIL_VALIDATOR, PASSWORD_VALIDATOR);
    private static final Validator<UserLoginForm> USER_LOGIN_FORM_VALIDATOR = new UserLoginFormValidator(EMAIL_VALIDATOR, PASSWORD_VALIDATOR);

    private static final UserDao USER_DAO = new UserDaoImpl(DATABASE_CONNECTOR);
    private static final TimeslotDao TIMESLOT_DAO = new TimeslotDaoImpl(DATABASE_CONNECTOR);
    private static final OrderDao ORDER_DAO = new OrderDaoImpl(DATABASE_CONNECTOR);
    private static final ServiceDao SERVICE_DAO = new ServiceDaoImpl(DATABASE_CONNECTOR);

    private static final Mapper<RoleEntity, Role> ROLE_MAPPER = new RoleMapper();
    private static final Mapper<UserEntity, User> USER_MAPPER = new UserMapper(ROLE_MAPPER);
    private static final Mapper<ServiceEntity, Service> SERVICE_MAPPER = new ServiceMapper();
    private static final Mapper<OrderEntity, Order> ORDER_MAPPER = new OrderMapper(USER_MAPPER, SERVICE_MAPPER);
    private static final Mapper<TimeslotEntity, Timeslot> TIMESLOT_MAPPER = new TimeslotMapper(ORDER_MAPPER);

    private static final AuthService AUTH_SERVICE = new AuthServiceImpl(USER_DAO, USER_VALIDATOR, USER_LOGIN_FORM_VALIDATOR, PASSWORD_ENCRYPTOR, USER_MAPPER);
    private static final UserService USER_SERVICE = new UserServiceImpl(USER_DAO, USER_MAPPER);
    private static final TimeslotService TIMESLOT_SERVICE = new TimeslotServiceImpl(TIMESLOT_DAO, ORDER_DAO, SERVICE_DAO, USER_DAO, TIMESLOT_MAPPER);
    private static final OrderService ORDER_SERVICE = new OrderServiceImpl(ORDER_DAO, SERVICE_DAO, USER_DAO, TIMESLOT_DAO, ORDER_MAPPER, SERVICE_MAPPER, TRANSACTION_MANAGER);
    private static final FeedbackService FEEDBACK_SERVICE = null; // todo impl

    private static final Map<String, Command> ROUTE_TO_COMMAND = initializeCommands();

    private ApplicationInjector() {
    }

    public Map<String, Command> getCommands() {
        return ROUTE_TO_COMMAND;
    }

    private static Map<String, Command> initializeCommands() {
        Map<String, Command> commands = new HashMap<>();

        commands.put("/", new HomeCommand());
        commands.put("/app/login", new LoginCommand(AUTH_SERVICE));
        commands.put("/app/logout", new LogoutCommand());
        commands.put("/app/signup", new RegisterCommand(AUTH_SERVICE));

        commands.put("/app/client/order-timeslot", new SelectTimeslotCommand(TIMESLOT_SERVICE));
        commands.put("/app/client/order-service", new SelectServiceCommand(ORDER_SERVICE));
        commands.put("/app/client/order-worker", new SelectWorkerCommand(USER_SERVICE));
        commands.put("/app/client/create-order", new CreateOrderCommand(ORDER_SERVICE));
        commands.put("/app/client/orders", new ShowOrdersCommand(ORDER_SERVICE));
        commands.put("/app/client/feedback", new ShowClientFeedbackByPagesCommand(FEEDBACK_SERVICE));
        commands.put("/app/client/leave-feedback", new LeaveFeedbackCommand(FEEDBACK_SERVICE));

        commands.put("/app/worker/timetable", new ShowWorkerTimetableCommand(TIMESLOT_SERVICE));
        commands.put("/app/worker/feedback", new ShowWorkerFeedbackByPagesCommand());

        commands.put("/app/admin/users", new ShowUsersByPagesCommand(USER_SERVICE));
        commands.put("/app/admin/timetable", new ShowAdminTimetableCommand(TIMESLOT_SERVICE));
        commands.put("/app/admin/feedback", new ShowUnapprovedFeedbackByPagesCommand());

        return commands;
    }

    public static ApplicationInjector getInstance() {
        return INSTANCE;
    }
}