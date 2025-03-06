package core.config;

import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PropertiesHolder {
    private static final Logger LOG = LoggerFactory.getLogger(PropertiesHolder.class);
    private static volatile PropertiesHolder instance = null;
    private ConfigProperties configProperties;

    private PropertiesHolder() {
        configProperties = ConfigFactory.create(ConfigProperties.class);
    }

    public static PropertiesHolder getInstance() {
        if (instance == null) {
            synchronized (PropertiesHolder.class) {
                if (instance == null) {
                    instance = new PropertiesHolder();
                }
            }
        }
        return instance;
    }

    public ConfigProperties getConfigProperties() {
        return configProperties;
    }

}