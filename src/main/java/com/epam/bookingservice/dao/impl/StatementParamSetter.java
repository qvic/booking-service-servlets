package com.epam.bookingservice.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementParamSetter<P> {

    void accept(PreparedStatement statement, P parameter, int position) throws SQLException;

    default void accept(PreparedStatement statement, P parameter) throws SQLException {
        accept(statement, parameter, 1);
    }
}
