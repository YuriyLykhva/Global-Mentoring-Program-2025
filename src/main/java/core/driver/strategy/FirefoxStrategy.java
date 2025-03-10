package core.driver.strategy;

import core.driver.WebConfiguration;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxStrategy extends AbstractDriverStrategy {
    public FirefoxStrategy(WebConfiguration webConfiguration) {
        super(webConfiguration);
    }

    @Override
    protected WebDriver getLocalDriverInstance() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--start-maximized");
        return new FirefoxDriver(options);
    }

    @Override
    protected DriverManagerType getDriverManagerType() {
        return DriverManagerType.FIREFOX;
    }
}
