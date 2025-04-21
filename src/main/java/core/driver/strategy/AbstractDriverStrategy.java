package core.driver.strategy;

import core.driver.RunType;
import core.driver.WebConfiguration;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractDriverStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDriverStrategy.class);

    public final WebConfiguration webConfiguration;

    protected AbstractDriverStrategy(WebConfiguration webConfiguration) {
        this.webConfiguration = webConfiguration;
    }

    protected abstract WebDriver getLocalDriverInstance();

    protected abstract DriverManagerType getDriverManagerType();

    public WebDriver getDriver() {
        return webConfiguration.getRunType() == RunType.LOCAL ?
                getLocalDriver() :
                getRemoteDriver();
    }

    protected WebDriver getRemoteDriver() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            String browserName = webConfiguration.getBrowserName();
            String platform = webConfiguration.getPlatform();
            String browserVersion = webConfiguration.getBrowserVersion();

            capabilities.setCapability("browserName", browserName);
            capabilities.setCapability("platformName", platform);

            capabilities.setBrowserName(browserName);
            capabilities.setVersion(browserVersion);
            capabilities.setPlatform(Platform.fromString(platform));

            boolean isLatest = Objects.nonNull(browserVersion) && "latest".equalsIgnoreCase(browserVersion);
            if (!isLatest) {
                capabilities.setCapability("browserVersion", browserVersion);
            }

            Map<String, Object> ltOptions = new HashMap<>();
            ltOptions.put("platformName", platform);
            ltOptions.put("project", "2025-project");
            ltOptions.put("build", "LambdaTest Build #" + System.currentTimeMillis());
            ltOptions.put("name", "Test - " + Thread.currentThread().getName());

            capabilities.setCapability("LT:Options", ltOptions);

            String remoteUrl = webConfiguration.getRemoteUrl()
                    .replace("{username}", webConfiguration.getRemoteUserName())
                    .replace("{accessKey}", webConfiguration.getRemoteAccessKey());

            LOGGER.info("Creating remote driver for {} version of {} on {}", browserVersion, browserName, platform);

            return new RemoteWebDriver(new URL(remoteUrl), capabilities);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create RemoteWebDriver", e);
        }
    }

    private WebDriver getLocalDriver() {
        WebDriverManager instance = WebDriverManager.getInstance(getDriverManagerType());
        String browserVersion = webConfiguration.getBrowserVersion();
        boolean isLatest = Objects.nonNull(browserVersion) && "latest".equalsIgnoreCase(browserVersion);
        if (!isLatest) {
            instance = instance.browserVersion(browserVersion);
        }
        instance.setup();
        LOGGER.info("Creating local driver.");
        return getLocalDriverInstance();
    }

}
