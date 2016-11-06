package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;

/**
 * Created by Сергей on 31.10.2016.
 */
public class ExeptionUtil {
    private ExeptionUtil(){}

    public static StorageException converExeption(SQLException e){
        if(e instanceof PSQLException){
            // см коды ошибок: http://www.postresql.org/docs/9.3/static/errcodes-appendix.html
            if(e.getSQLState().equals("23505")){
                return new ExistStorageException(null);
            }
        }
        return new StorageException(e);
    }
}
