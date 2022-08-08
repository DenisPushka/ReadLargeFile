import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootTest
public class testMain {

    private static final Logger LOGGER = Logger.getLogger(BatchProperties.Jdbc.class.getName());
    private static Optional<Connection> connection;

    @Test
    public Optional<Connection> connect() {
        if (connection.isEmpty()) {
            String url = "jdbc:postgresql://localhost:5432/TEST_JAVA";
            String user = "postgres";
            String password = "ogr84Bqk3";

            try {
                connection = Optional.ofNullable(DriverManager.getConnection(url, user, password));
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        return connection;
    }

    public Optional get() {
        return connection.flatMap(conn -> {
                    Optional customer = Optional.empty();
                    String sql = "SELECT * FROM buffer WHERE customer_id = " + 11;

                    try (Statement statement = conn.createStatement();
                         ResultSet resultSet = statement.executeQuery(sql)) {

                        if (resultSet.next()) {
                            var name = resultSet.getString("name");
                            var text = resultSet.getString("text");
                            var number = resultSet.getInt("number");
                            var number2 = resultSet.getArray("number2");
                            var textArray = resultSet.getArray("textArray");
//
//                    customer = Optional.of(
//                            new Data(name, text, number, number2,textArray));

                            LOGGER.log(Level.INFO, "Found {0} in database", customer.get());
                        }
                    } catch (SQLException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }

                    return customer;
                }
        );
    }
}
