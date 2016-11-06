package com.urise.webapp.sql;

import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Сергей on 31.10.2016.
 */
public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute(String sql) {
        execute(sql, new SqlExecutor<Boolean>() {
            @Override
            public Boolean execute(PreparedStatement ps) throws SQLException {
                return ps.execute();
            }
        });
    }

    public <T> T execute(String sql, SqlExecutor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw ExeptionUtil.converExeption(e);
        }
    }

    public <T> T transactionExecute(SqlTransaction<T> executor){
        try(Connection conn=connectionFactory.getConnection()){
            try{
                conn.setAutoCommit(false);
                T res=executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e){
                conn.rollback();
                throw ExeptionUtil.converExeption(e);
            }
        }catch (SQLException e){
            throw new StorageException(e);
        }
    }

}
