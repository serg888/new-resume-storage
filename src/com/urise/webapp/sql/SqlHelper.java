package com.urise.webapp.sql;

import com.urise.webapp.exception.StorageException;

import java.sql.*;

/**
 * Created by Сергей on 30.10.2016.
 */
public class SqlHelper{
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = new ConnectionFactory() {
            @Override
            public Connection getConnection() throws SQLException {
                return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            }
        };
    }

    public void execute(String... args) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(args[0])) {
            for (int i = 1; i < args.length; i++) {
                ps.setString(i, args[i]);
            }
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public Object executeQuery(SqlQueryExecutor sqlQueryExecutor, String... args) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(args[0])) {
            for (int i = 1; i < args.length; i++) {
                ps.setString(i, args[i]);
            }
            return sqlQueryExecutor.execute(ps.executeQuery());
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }


}
