package com.salon.booking.context;

import com.salon.booking.command.Command;
import com.salon.booking.command.HomeCommand;
import com.salon.booking.command.admin.ApproveFeedbackCommand;
import com.salon.booking.command.admin.PromoteClientToWorkerCommand;
import com.salon.booking.command.admin.ShowAdminTimetableCommand;
import com.salon.booking.command.admin.ShowUnapprovedFeedbackByPagesCommand;
import com.salon.booking.command.admin.ShowClientsByPagesCommand;
import com.salon.booking.command.admin.ShowWorkersByPagesCommand;
import com.salon.booking.command.client.CreateOrderCommand;
import com.salon.booking.command.client.LeaveFeedbackCommand;
import com.salon.booking.command.client.SelectServiceCommand;
import com.salon.booking.command.client.SelectTimeslotCommand;
import com.salon.booking.command.client.SelectWorkerCommand;
import com.salon.booking.command.client.ShowClientFeedbackByPagesCommand;
import com.salon.booking.command.client.ShowOrdersCommand;
import com.salon.booking.command.user.LoginCommand;
import com.salon.booking.command.user.LogoutCommand;
import com.salon.booking.command.user.RegisterCommand;
import com.salon.booking.command.worker.ShowWorkerFeedbackByPagesCommand;
import com.salon.booking.command.worker.ShowWorkerTimetableCommand;
import com.salon.booking.dao.FeedbackDao;
import com.salon.booking.dao.OrderDao;
import com.salon.booking.dao.ServiceDao;
import com.salon.booking.dao.TimeslotDao;
import com.salon.booking.dao.UserDao;
import com.salon.booking.dao.impl.FeedbackDaoImpl;
import com.salon.booking.dao.impl.OrderDaoImpl;
import com.salon.booking.dao.impl.ServiceDaoImpl;
import com.salon.booking.dao.impl.TimeslotDaoImpl;
import com.salon.booking.dao.impl.connector.DataSourceConnector;
import com.salon.booking.dao.impl.connector.DataSourceTransactionManager;
import com.salon.booking.dao.impl.connector.HikariDataSourceConnector;
import com.salon.booking.domain.Feedback;
import com.salon.booking.domain.FeedbackStatus;
import com.salon.booking.domain.Order;
import com.salon.booking.domain.Role;
import com.salon.booking.domain.Service;
import com.salon.booking.domain.Timeslot;
import com.salon.booking.domain.User;
import com.salon.booking.domain.UserLoginForm;
import com.salon.booking.entity.FeedbackEntity;
import com.salon.booking.entity.FeedbackStatusEntity;
import com.salon.booking.entity.OrderEntity;
import com.salon.booking.entity.RoleEntity;
import com.salon.booking.entity.ServiceEntity;
import com.salon.booking.entity.TimeslotEntity;
import com.salon.booking.entity.UserEntity;
import com.salon.booking.mapper.FeedbackMapper;
import com.salon.booking.mapper.FeedbackStatusMapper;
import com.salon.booking.mapper.Mapper;
import com.salon.booking.mapper.OrderMapper;
import com.salon.booking.mapper.RoleMapper;
import com.salon.booking.mapper.ServiceMapper;
import com.salon.booking.mapper.TimeslotMapper;
import com.salon.booking.mapper.UserMapper;
import com.salon.booking.service.FeedbackService;
import com.salon.booking.service.OrderService;
import com.salon.booking.service.TimeslotService;
import com.salon.booking.service.UserService;
import com.salon.booking.service.encoder.PasswordEncoder;
import com.salon.booking.service.impl.FeedbackServiceImpl;
import com.salon.booking.service.impl.OrderServiceImpl;
import com.salon.booking.service.impl.TimeslotServiceImpl;
import com.salon.booking.service.impl.AuthServiceImpl;
import com.salon.booking.service.impl.UserServiceImpl;
import com.salon.booking.service.validator.EmailValidator;
import com.salon.booking.dao.impl.UserDaoImpl;
import com.salon.booking.service.AuthService;
import com.salon.booking.service.validator.FeedbackTextValidator;
import com.salon.booking.service.validator.PasswordValidator;
import com.salon.booking.service.validator.UserLoginFormValidator;
import com.salon.booking.service.validator.UserValidator;
import com.salon.booking.service.validator.Validator;

import java.util.HashMap;
import java.util.Map;

public final class ApplicationInjector {

    private static final ApplicationInjector INSTANCE = new ApplicationInjector();

    private static final DataSourceTransactionManager TRANSACTION_MANAGER = new DataSourceTransactionManager();

    private static final DataSourceConnector DATABASE_CONNECTOR = new HikariDataSourceConnector(
            "db", TRANSACTION_MANAGER);

    private static final PasswordEncoder PASSWORD_ENCRYPTOR = new PasswordEncoder();
    private static final Validator<String> PASSWORD_VALIDATOR = new PasswordValidator();
    private static final Validator<String> EMAIL_VALIDATOR = new EmailValidator();
    private static final Validator<String> FEEDBACK_TEXT_VALIDATOR = new FeedbackTextValidator();
    private static final Validator<User> USER_VALIDATOR = new UserValidator(EMAIL_VALIDATOR, PASSWORD_VALIDATOR);
    private static final Validator<UserLoginForm> USER_LOGIN_FORM_VALIDATOR = new UserLoginFormValidator(EMAIL_VALIDATOR);

    private static final UserDao USER_DAO = new UserDaoImpl(DATABASE_CONNECTOR);
    private static final TimeslotDao TIMESLOT_DAO = new TimeslotDaoImpl(DATABASE_CONNECTOR);
    private static final OrderDao ORDER_DAO = new OrderDaoImpl(DATABASE_CONNECTOR);
    private static final ServiceDao SERVICE_DAO = new ServiceDaoImpl(DATABASE_CONNECTOR);
    private static final FeedbackDao FEEDBACK_DAO = new FeedbackDaoImpl(DATABASE_CONNECTOR);

    private static final Mapper<RoleEntity, Role> ROLE_MAPPER = new RoleMapper();
    private static final Mapper<UserEntity, User> USER_MAPPER = new UserMapper(ROLE_MAPPER);
    private static final Mapper<ServiceEntity, Service> SERVICE_MAPPER = new ServiceMapper();
    private static final Mapper<OrderEntity, Order> ORDER_MAPPER = new OrderMapper(USER_MAPPER, SERVICE_MAPPER);
    private static final Mapper<TimeslotEntity, Timeslot> TIMESLOT_MAPPER = new TimeslotMapper(ORDER_MAPPER);
    private static final Mapper<FeedbackEntity, Feedback> FEEDBACK_MAPPER = new FeedbackMapper(USER_MAPPER);
    private static final Mapper<FeedbackStatusEntity, FeedbackStatus> FEEDBACK_STATUS_MAPPER = new FeedbackStatusMapper();

    private static final AuthService AUTH_SERVICE = new AuthServiceImpl(USER_DAO, USER_VALIDATOR,
            USER_LOGIN_FORM_VALIDATOR, PASSWORD_ENCRYPTOR, USER_MAPPER);

    private static final UserService USER_SERVICE = new UserServiceImpl(USER_DAO, ORDER_DAO, USER_MAPPER, TRANSACTION_MANAGER);

    private static final TimeslotService TIMESLOT_SERVICE = new TimeslotServiceImpl(TIMESLOT_DAO, ORDER_DAO, SERVICE_DAO,
            USER_DAO, TIMESLOT_MAPPER);

    private static final OrderService ORDER_SERVICE = new OrderServiceImpl(TIMESLOT_SERVICE, ORDER_DAO, SERVICE_DAO,
            USER_DAO, ORDER_MAPPER, SERVICE_MAPPER, TRANSACTION_MANAGER);

    private static final FeedbackService FEEDBACK_SERVICE = new FeedbackServiceImpl(FEEDBACK_DAO, ORDER_SERVICE, FEEDBACK_MAPPER,
            FEEDBACK_STATUS_MAPPER, FEEDBACK_TEXT_VALIDATOR);

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
        commands.put("/app/client/leave-feedback", new LeaveFeedbackCommand(FEEDBACK_SERVICE, ORDER_SERVICE));

        commands.put("/app/worker/timetable", new ShowWorkerTimetableCommand(TIMESLOT_SERVICE));
        commands.put("/app/worker/feedback", new ShowWorkerFeedbackByPagesCommand());

        commands.put("/app/admin/clients", new ShowClientsByPagesCommand(USER_SERVICE));
        commands.put("/app/admin/workers", new ShowWorkersByPagesCommand(USER_SERVICE));
        commands.put("/app/admin/timetable", new ShowAdminTimetableCommand(TIMESLOT_SERVICE));
        commands.put("/app/admin/feedback", new ShowUnapprovedFeedbackByPagesCommand(FEEDBACK_SERVICE));
        commands.put("/app/admin/approve-feedback", new ApproveFeedbackCommand(FEEDBACK_SERVICE));
        commands.put("/app/admin/promote-client", new PromoteClientToWorkerCommand(USER_SERVICE));

        return commands;
    }

    public static ApplicationInjector getInstance() {
        return INSTANCE;
    }
}