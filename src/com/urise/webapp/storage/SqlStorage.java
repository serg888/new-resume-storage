package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlExecutor;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.sql.SqlTransaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Сергей on 29.10.2016.
 */
public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(new ConnectionFactory() {
            @Override
            public Connection getConnection() throws SQLException {
                return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            }
        });
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE  FROM resume");
    }

    @Override
    public void update(Resume r) {
        sqlHelper.<Void>execute("UPDATE resume SET full_name=? WHERE uuid=?", new SqlExecutor<Void>() {
            @Override
            public Void execute(PreparedStatement st) throws SQLException {
                st.setString(1, r.getFullName());
                st.setString(2, r.getUuid());
                if (st.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
                return null;
            }
        });
    }


    @Override
    public void save(Resume r) {
        sqlHelper.transactionExecute(new SqlTransaction<Object>() {
            @Override
            public Object execute(Connection conn) throws SQLException {
                try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume(uuid, full_name)VALUES (?,?)")) {
                    ps.setString(1, r.getUuid());
                    ps.setString(2, r.getFullName());
                    ps.execute();
                }
                try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact(type,value,resume_uuid) VALUES (?,?,?)")) {
                    for (Map.Entry<ContactType, String> map : r.getContacts().entrySet()) {
                        ps.setString(1, map.getKey().name());
                        ps.setString(2, map.getValue());
                        ps.setString(3, r.getUuid());
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
                return null;
            }
        });
    }


    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume r " +
                " LEFT JOIN contact c " +
                "    ON r.uuid=c.resume_uuid " +
                " WHERE r.uuid=?", new SqlExecutor<Resume>() {
            @Override
            public Resume execute(PreparedStatement ps) throws SQLException {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                Resume r = new Resume(uuid, rs.getString("full_name"));
                do {
                    r.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                } while (rs.next());

                return r;
            }
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.<Void>execute("DELETE FROM resume WHERE uuid=?", new SqlExecutor<Void>() {
            @Override
            public Void execute(PreparedStatement st) throws SQLException {
                st.setString(1, uuid);
                if (st.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
                ;
                return null;
            }
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Storage storage = new ListStorage();
        sqlHelper.execute("SELECT * FROM resume", new SqlExecutor<Void>() {
            @Override
            public Void execute(PreparedStatement st) throws SQLException {
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    storage.save(new Resume(rs.getString("uuid"), rs.getString("full_name")));
                }
                return null;
            }
        });
        sqlHelper.execute("SELECT * FROM contact", new SqlExecutor<Void>() {
            @Override
            public Void execute(PreparedStatement st) throws SQLException {
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Resume r = storage.get(rs.getString("resume_uuid"));
                    ContactType ct = ContactType.valueOf(rs.getString("type"));
                    r.addContact(ct, rs.getString("value"));
                    storage.update(r);
                }
                return null;
            }
        });
        return storage.getAllSorted();
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", new SqlExecutor<Integer>() {
            @Override
            public Integer execute(PreparedStatement st) throws SQLException {
                ResultSet rs = st.executeQuery();
                return (rs.next()) ? rs.getInt(1) : 0;
            }
        });
    }
}
