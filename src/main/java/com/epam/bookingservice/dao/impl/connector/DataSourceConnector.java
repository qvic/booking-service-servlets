package com.epam.bookingservice.dao.impl.connector;

import java.sql.SQLException;

public interface DataSourceConnector {

    DataSourceConnection getConnection() throws SQLException;
}
