package core.driver.strategy;

import core.driver.RunType;
import core.driver.WebConfiguration;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.WebDriver;
import java.util.Objects;

public abstract class AbstractDriverStrategy {

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

    private WebDriver getRemoteDriver() {
        throw new java.lang.UnsupportedOperationException("The method getRemoteDriver() is not implemented yet!");
    }

    private WebDriver getLocalDriver() {
        WebDriverManager instance = WebDriverManager.getInstance(getDriverManagerType());
        String browserVersion = webConfiguration.getBrowserVersion();
        boolean isLatest = Objects.nonNull(browserVersion) && "latest".equalsIgnoreCase(browserVersion);
        if (!isLatest) {
            instance = instance.browserVersion(browserVersion);
        }
        instance.setup();
        return getLocalDriverInstance();
    }

}
