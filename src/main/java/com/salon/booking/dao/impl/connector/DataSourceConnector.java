package com.salon.booking.dao.impl.connector;

import java.sql.SQLException;

public interface DataSourceConnector {

    DataSourceConnection getConnection() throws SQLException;
}
