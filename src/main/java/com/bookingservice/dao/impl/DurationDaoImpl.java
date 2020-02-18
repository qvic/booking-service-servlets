package com.bookingservice.dao.impl;

import com.bookingservice.dao.DurationDao;
import com.bookingservice.dao.impl.connector.DataSourceConnector;
import com.bookingservice.entity.DurationEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DurationDaoImpl extends AbstractReadOnlyDaoImpl<DurationEntity> implements DurationDao {

    private static final String FIND_BY_ID_QUERY = "SELECT d.* FROM duration d WHERE d.id = ?";
    private static final String FIND_ALL_QUERY = "SELECT d.* FROM duration d";
    private static final String COUNT_QUERY = "SELECT count(*) FROM duration d";

    public DurationDaoImpl(DataSourceConnector connector) {
        super(connector, new ReadQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, COUNT_QUERY));
    }

    @Override
    protected DurationEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new DurationEntity(
                resultSet.getInt("int"),
                resultSet.getInt("minutes"));
    }
}
