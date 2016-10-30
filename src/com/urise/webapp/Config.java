package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Сергей on 29.10.2016.
 */
public class Config {
    private static final Config INSTANCE = new Config();
    private static File PROPS;
    private Properties props = new Properties();
    private File storageDir;
    private String dbUrl,dbUser, dbPassword;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        PROPS = new File("./config/resumes.properties");
        try (
                InputStream is = new FileInputStream(PROPS);
        ) {
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            dbUrl=props.getProperty("db.url");
            dbUser=props.getProperty("db.user");
            dbPassword=props.getProperty("db.password");

        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file" + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }
}
