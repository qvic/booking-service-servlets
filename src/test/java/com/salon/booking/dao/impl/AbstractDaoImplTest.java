package com.salon.booking.dao.impl;

import com.salon.booking.dao.impl.connector.DataSourceConnection;
import com.salon.booking.dao.impl.connector.DataSourceConnector;
import com.salon.booking.dao.CrudDao;
import io.zonky.test.db.postgres.junit.EmbeddedPostgresRules;
import io.zonky.test.db.postgres.junit.SingleInstancePostgresRule;
import org.junit.Before;
import org.junit.Rule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AbstractDaoImplTest {

    private static final String SQL_SCHEMA_PATH = "sql/schema.sql";
    private static final String SQL_DATA_PATH = "src/test/resources/test_data.sql";

    @Rule
    public SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

    DataSourceConnector connector = () -> new DataSourceConnection(pg.getEmbeddedPostgres().getPostgresDatabase().getConnection(), false);

    @Before
    public void initializeDatabase() {
        try {
            try (DataSourceConnection connection = connector.getConnection()) {
                Statement statement = connection.getOriginal().createStatement();

                statement.executeUpdate(new String(Files.readAllBytes(Paths.get(SQL_SCHEMA_PATH))));
                statement.executeUpdate(new String(Files.readAllBytes(Paths.get(SQL_DATA_PATH))));
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Exception during Embedded Postgres test database initialization", e);
        }
    }

    <E> void testDaoMapping(CrudDao<E> dao, E entityToBeSaved, Function<E, Integer> idExtractor, String errorMessage) {
        E savedEntity = dao.save(entityToBeSaved);

        Optional<E> fetchedEntity = dao.findById(idExtractor.apply(savedEntity));

        assertTrue(errorMessage, fetchedEntity.isPresent());
        assertEquals(savedEntity, fetchedEntity.get());
    }
}
