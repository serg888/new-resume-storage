package com.urise.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Сергей on 31.10.2016.
 */
public interface SqlExecutor<T> {
    T execute(PreparedStatement st) throws SQLException;
}
