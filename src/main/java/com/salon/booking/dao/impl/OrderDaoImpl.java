package com.salon.booking.dao.impl;

import com.salon.booking.dao.OrderDao;
import com.salon.booking.dao.impl.connector.DataSourceConnector;
import com.salon.booking.entity.OrderEntity;
import com.salon.booking.entity.ServiceEntity;
import com.salon.booking.entity.UserEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDaoImpl extends AbstractPageableCrudDaoImpl<OrderEntity> implements OrderDao {

    private static final String FIND_BY_ID_QUERY = "SELECT o.* FROM \"order\" o WHERE o.id = ?";
    private static final String FIND_ALL_QUERY = "SELECT o.* FROM \"order\" o";
    private static final String FIND_ALL_PAGED_QUERY = "SELECT o.* FROM \"order\" o OFFSET ? LIMIT ?";
    private static final String FIND_ALL_BY_CLIENT = "SELECT o.* FROM \"order\" o WHERE o.client_id = ?";
    private static final String FIND_ALL_BY_WORKER = "SELECT o.* FROM \"order\" o WHERE o.worker_id = ?";
    private static final String FIND_ALL_FINISHED_AFTER = "SELECT DISTINCT o.* FROM \"order\" o INNER JOIN order_timeslot ot ON o.id = ot.order_id INNER JOIN timeslot t ON ot.timeslot_id = t.id WHERE t.date + t.from_time < ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM order";

    private static final String SAVE_QUERY = "INSERT INTO \"order\" (date, worker_id, client_id, service_id) VALUES (?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE \"order\" SET date = ?, worker_id = ?, client_id = ?, service_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM \"order\" WHERE id = ?";

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
    public List<OrderEntity> findAllFinishedAfter(LocalDateTime date) {
        return findAllByParam(date, FIND_ALL_FINISHED_AFTER, LOCAL_DATE_TIME_SETTER);
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
        statement.setInt(5, entity.getId());
    }

    private void populateNonIdFields(OrderEntity entity, PreparedStatement statement) throws SQLException {
        statement.setTimestamp(1, Timestamp.valueOf(entity.getDate()));
        statement.setInt(2, entity.getWorker().getId());
        statement.setInt(3, entity.getClient().getId());
        statement.setInt(4, entity.getService().getId());
    }
}
