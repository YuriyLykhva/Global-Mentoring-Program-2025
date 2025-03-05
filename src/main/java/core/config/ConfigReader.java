package core.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final String CONFIG_FILE_PATH = "src/test/resources/local.properties";
    //todo to setup config.properties file based on environment
    private static Properties properties = new Properties();

    static {
        try (FileInputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + CONFIG_FILE_PATH, e);
        }
    }

    public static String getProperty(String key) {
        return getProperty(key, null);
    }
    
    public static long getLongProperty(String key, long defaultValue) {
        return Long.parseLong(getProperty(key, String.valueOf(defaultValue)));
    }
    
    public static String getProperty(String key, String defaultValue) {
        String envProp = System.getenv(key);
        if(envProp != null){
            return envProp;
        }
        String fromConfig = properties.getProperty(key);
        if(fromConfig != null){
            return fromConfig;
        }
        if(defaultValue != null){
            return defaultValue;
        }
        throw new RuntimeException(String.format("Property '%s' is not defined", key));
    }
}
