package com.urise.webapp.sql;

import java.sql.ResultSet;

/**
 * Created by Сергей on 30.10.2016.
 */
public interface SqlQueryExecutor<T> {
    public T execute(ResultSet resultSet);
}
