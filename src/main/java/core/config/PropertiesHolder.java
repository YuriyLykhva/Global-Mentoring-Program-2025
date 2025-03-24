package core.config;

import org.aeonbits.owner.ConfigFactory;

public final class PropertiesHolder {
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