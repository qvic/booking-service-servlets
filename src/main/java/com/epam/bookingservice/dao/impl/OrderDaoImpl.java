package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.OrderDao;
import com.epam.bookingservice.dao.impl.connector.DataSourceConnector;
import com.epam.bookingservice.entity.OrderEntity;
import com.epam.bookingservice.entity.OrderStatusEntity;
import com.epam.bookingservice.entity.ServiceEntity;
import com.epam.bookingservice.entity.TimeslotEntity;
import com.epam.bookingservice.entity.UserEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class OrderDaoImpl extends AbstractPageableCrudDaoImpl<OrderEntity> implements OrderDao {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM \"order\" o WHERE o.id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM \"order\" o";
    private static final String FIND_ALL_PAGED_QUERY = "SELECT * FROM \"order\" o OFFSET ? LIMIT ?";
    private static final String FIND_ALL_BY_CLIENT = "SELECT * FROM \"order\" o WHERE o.client_id = ?";
    private static final String FIND_ALL_BY_WORKER = "SELECT * FROM \"order\" o WHERE o.worker_id = ?";

    private static final String SAVE_QUERY = "INSERT INTO \"order\" (date, worker_id, client_id, timeslot_id, status_id, service_id) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE \"order\" SET date = ?, worker_id = ?, client_id = ?, timeslot_id = ?, status_id = ?, service_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM \"order\" WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM order";

    public OrderDaoImpl(DataSourceConnector connector) {
        super(connector, new PageableCrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, SAVE_QUERY, UPDATE_QUERY,
                DELETE_QUERY, COUNT_QUERY, FIND_ALL_PAGED_QUERY));
    }

    @Override
    public List<OrderEntity> findAllByClientId(Integer id) {
        return findAllByParam(id, FIND_ALL_BY_CLIENT, INT_SETTER);
    }

    @Override
    public List<OrderEntity> findAllByWorkerId(Integer id) {
        return findAllByParam(id, FIND_ALL_BY_WORKER, INT_SETTER);
    }

    @Override
    protected OrderEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {

        return OrderEntity.builder()
                .setId(resultSet.getInt("id"))
                .setClient(UserEntity.builder()
                        .setId(resultSet.getInt("client_id"))
                        .build())
                .setWorker(UserEntity.builder()
                        .setId(resultSet.getInt("worker_id"))
                        .build())
                .setDate(resultSet.getTimestamp("date").toLocalDateTime())
                .setService(ServiceEntity.builder()
                        .setId(resultSet.getInt("service_id"))
                        .build())
                .setTimeslot(TimeslotEntity.builder()
                        .setId(resultSet.getInt("timeslot_id"))
                        .build())
                .setStatus(OrderStatusEntity.getById(resultSet.getInt("status_id")))
                .build();
    }

    @Override
    protected OrderEntity applyGeneratedKeysToEntity(OrderEntity entity, ResultSet generatedKeys) throws SQLException {
        return OrderEntity.builder(entity)
                .setId(generatedKeys.getInt("id"))
                .build();
    }

    @Override
    protected void populateInsertStatement(OrderEntity entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
    }

    @Override
    protected void populateUpdateStatement(OrderEntity entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
        statement.setInt(7, entity.getId());
    }

    private void populateNonIdFields(OrderEntity entity, PreparedStatement statement) throws SQLException {
        System.out.println(entity);
        statement.setTimestamp(1, Timestamp.valueOf(entity.getDate()));
        statement.setInt(2, entity.getWorker().getId());
        statement.setInt(3, entity.getClient().getId());
        statement.setInt(4, entity.getTimeslot().getId());
        statement.setInt(5, entity.getStatus().getId());
        statement.setInt(6, entity.getService().getId());
    }
}
