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
    private Properties props=new Properties();
    private File storageDir;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
       // System.out.println("Config");
        PROPS=new File("./config/resumes.properties");
       // System.out.println(PROPS);
        try (
                InputStream is = new FileInputStream(PROPS);
        ) {
            System.out.println("before");
            props.load(is);
            System.out.println("props.load");;
            storageDir=new File(props.getProperty("storage.dir"));
            System.out.println(storageDir);

        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file"+PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }
}
