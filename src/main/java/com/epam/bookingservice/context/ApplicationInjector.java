package com.epam.bookingservice.context;

import com.epam.bookingservice.dao.OrderDao;
import com.epam.bookingservice.dao.ReviewDao;
import com.epam.bookingservice.dao.ServiceDao;
import com.epam.bookingservice.dao.UserDao;
import com.epam.bookingservice.dao.impl.OrderDaoImpl;
import com.epam.bookingservice.dao.impl.ReviewDaoImpl;
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
import com.epam.bookingservice.utility.SimpleDatabaseConnector;
import com.epam.bookingservice.utility.ResourceManager;

public class ApplicationInjector {

    private static final ApplicationInjector INSTANCE = new ApplicationInjector();

    private static final Validator<User> USER_VALIDATOR = new UserValidator();

    private static final PasswordEncryptor PASSWORD_ENCRYPTOR = new PasswordEncryptor();

    private static final DatabaseConnector DATABASE_CONNECTOR = new SimpleDatabaseConnector(Config.DB_SETTINGS_BUNDLE_NAME);

    private static final UserDao USER_DAO = new UserDaoImpl(DATABASE_CONNECTOR);
    private static final ServiceDao SERVICE_TYPE_DAO = new ServiceDaoImpl(DATABASE_CONNECTOR);
    private static final RoleDao ROLE_DAO = new RoleDaoImpl(DATABASE_CONNECTOR);
    private static final ReviewDao REVIEW_DAO = new ReviewDaoImpl(DATABASE_CONNECTOR);
    private static final OrderDao APPOINTMENT_DAO = new OrderDaoImpl(DATABASE_CONNECTOR, USER_DAO, SERVICE_TYPE_DAO);

    private static final UserService USER_SERVICE = new UserServiceImpl(USER_DAO, USER_VALIDATOR, PASSWORD_ENCRYPTOR);

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

    public ServiceDao getServiceTypeDao() {
        return SERVICE_TYPE_DAO;
    }

    public RoleDao getRoleDao() {
        return ROLE_DAO;
    }

    public ReviewDao getReviewDao() {
        return REVIEW_DAO;
    }

    public OrderDao getAppointmentDao() {
        return APPOINTMENT_DAO;
    }

    private ApplicationInjector() {
    }
}