package com.epam.bookingservice.utility;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnector {

    Connection getConnection() throws SQLException;
}
