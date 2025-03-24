package core.driver;

import core.driver.strategy.AbstractDriverStrategy;
import core.driver.strategy.ChromeStrategy;
import core.driver.strategy.FirefoxStrategy;

import java.util.Map;

public class DriverMapping {
    private static final Map<String, Class<? extends AbstractDriverStrategy>> DRIVER_MAPPINGS =
            Map.of("chrome", ChromeStrategy.class, "firefox", FirefoxStrategy.class);

    public static AbstractDriverStrategy getDriverStrategy(WebConfiguration webConfiguration) {
        String driverName = webConfiguration.getBrowserName();

        if (!DRIVER_MAPPINGS.containsKey(driverName)) {
            throw new RuntimeException("Driver not supported: " + driverName);
        }

        try {
            return DRIVER_MAPPINGS.get(driverName)
                    .getConstructor(WebConfiguration.class)
                    .newInstance(webConfiguration);
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate driver strategy for: " + driverName, e);
        }
    }
}