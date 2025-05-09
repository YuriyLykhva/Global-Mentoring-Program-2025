package core.driver.strategy;

import core.driver.WebConfiguration;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeStrategy extends AbstractDriverStrategy {

        public ChromeStrategy(WebConfiguration webConfiguration) {
            super(webConfiguration);
        }

        @Override
        protected WebDriver getLocalDriverInstance() {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--remote-allow-origins=*");
            return new ChromeDriver(options);
        }

        @Override
        protected DriverManagerType getDriverManagerType() {
            return DriverManagerType.CHROME;
        }
}
