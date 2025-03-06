package core.config;

import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;
import java.util.function.Function;

public final class PropertiesHolder {
    private static final Logger LOG = LoggerFactory.getLogger(PropertiesHolder.class);
    private static volatile PropertiesHolder instance = null;
    private ConfigProperties configProperties;
//    private final EnvironmentProperties environmentProperties;

    private PropertiesHolder() {
        configProperties = ConfigFactory.create(ConfigProperties.class);

//        updateConfigProperty(Const.ENV, ConfigProperties::getEnvToRun, false);
//
//        environmentProperties = ConfigFactory.create(EnvironmentProperties.class);
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

//    public EnvironmentProperties getEnvironmentProperties() {
//        return environmentProperties;
//    }

//    public String get(String key) {
//        return PropMgr.get(key);
//    }

    private void updateConfigProperty(String propertyKey, Function<ConfigProperties, String> alternative, boolean isSecret) {
        var valueEnvVariable = System.getProperty(propertyKey);
        var value = Objects.isNull(valueEnvVariable) ? alternative.apply(configProperties) : valueEnvVariable;
        var valueToDisplay = isSecret ? "***" : value;
        LOG.info("The property '{}' is initialized with the '{}' value", propertyKey, valueToDisplay);
        ConfigFactory.setProperty(propertyKey, value);
        configProperties = ConfigFactory.create(ConfigProperties.class, ConfigFactory.getProperties());
    }
}