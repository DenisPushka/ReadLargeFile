package com.example.readLargeFile.connect;

import com.example.readLargeFile.model.Information;
import org.springframework.boot.autoconfigure.batch.BatchProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connect {
    private static final Logger LOGGER = Logger.getLogger(BatchProperties.Jdbc.class.getName());
    private static Optional<Connection> connection = Optional.empty();
    private static final String SQL_FIND_GOAL = "UPDATE public.receive_data\n" +
            "\tSET array_text = ':texts', numbers=':numb'\n" +
            "\tWHERE id= :number;\n";

    public static Optional<Connection> connect() {
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

    public static boolean get(List<Information> informationList) {
        for (var info : informationList) {
            var buf = collection(info.getArray_text());
            var sql = SQL_FIND_GOAL.replace(":texts", buf);
            sql = sql.replace(":number", info.getId().toString());
            buf = collection(info.getNumbers());
            var finalSql = sql.replace(":numb", buf);

            connection.flatMap(conn -> {
                try {
                    Statement statement = conn.createStatement();
                    statement.executeUpdate(finalSql);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return Optional.empty();
            });
        }
        return true;
    }

    private static String collection(List<?> collection) {
        var i = 0;
        var buffer = new StringBuilder().append('{');

        for (var type : collection) {
            if (i < collection.size() - 1) buffer.append(type).append(",");
            else buffer.append(type);
            i++;
        }

        buffer.append('}');
        return buffer.toString();
    }
}
