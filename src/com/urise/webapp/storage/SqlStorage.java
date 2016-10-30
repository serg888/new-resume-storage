package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.sql.SqlQueryExecutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Сергей on 29.10.2016.
 */
public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume r) {
        sqlHelper.execute("UPDATE resume SET full_name=? WHERE uuid=?", r.getFullName(), r.getUuid());
    }

    @Override
    public void save(Resume r) {
        sqlHelper.execute("INSERT INTO resume(uuid, full_name)VALUES (?,?)", r.getUuid(), r.getFullName());
    }

    @Override
    public Resume get(String uuid) {
        Resume r=(Resume)sqlHelper.executeQuery(new SqlQueryExecutor<Resume>() {
            @Override
            public Resume execute(ResultSet resultSet) {
                try {
                    if (!resultSet.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, resultSet.getString("full_name"));
                    return r;
                } catch (SQLException e) {
                    throw new StorageException(e);
                }
            }
        },"SELECT * FROM resume r WHERE r.uuid=?", uuid);
       return r;
    }

    @Override
    public void delete(String uuid) {
        String s = "DELETE FROM resume WHERE uuid=?";
        sqlHelper.execute(s, uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = (List<Resume>) sqlHelper.executeQuery(new SqlQueryExecutor<List<Resume>>() {
            @Override
            public List<Resume> execute(ResultSet resultSet) {
                List<Resume> list = new ArrayList<>();
                try {
                    while (resultSet.next()) {
                        list.add(new Resume(resultSet.getString("uuid").trim(), resultSet.getString("full_name")));
                    }
                } catch (SQLException e) {
                    throw new StorageException(e);
                }

                Collections.sort(list);
                return list;
            }
        }, "SELECT * FROM resume");
        return list;
    }

    @Override
    public int size() {

        Integer size = (Integer)sqlHelper.executeQuery(new SqlQueryExecutor<Integer>() {
            @Override
            public Integer execute(ResultSet resultSet) {
                int size = 0;
                try {
                    while (resultSet.next()) {
                        size++;
                    }
                } catch (SQLException e) {
                    throw new StorageException(e);
                }
                return size;
            }
        }, "SELECT * FROM resume");
        return size;
    }
}
