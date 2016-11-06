package com.urise.webapp.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Сергей on 05.11.2016.
 */
public interface SqlTransaction<T> {
    T execute(Connection conn) throws SQLException;
}
