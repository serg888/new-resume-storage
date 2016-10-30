package com.urise.webapp.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Сергей on 29.10.2016.
 */
public interface ConnectionFactory {
    Connection getConnection() throws SQLException;
}
