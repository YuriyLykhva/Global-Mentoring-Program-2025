package core.driver.strategy;

import core.driver.RunType;
import core.driver.WebConfiguration;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;

import java.net.URL;
import java.time.Duration;
import java.util.Objects;

public abstract class AbstractDriverStrategy {

    private final WebConfiguration webConfiguration;

    protected AbstractDriverStrategy(WebConfiguration webConfiguration) {
        this.webConfiguration = webConfiguration;
    }

    protected abstract WebDriver getLocalDriverInstance();

//    protected abstract AbstractDriverOptions<?> getSpecificRemoteDriverOptions();

    protected abstract DriverManagerType getDriverManagerType();

    public WebDriver getDriver() {
        return webConfiguration.getRunType() == RunType.LOCAL ?
                getLocalDriver() :
                getRemoteDriver();
    }

    //todo: implement getRemoteDriver method
    private WebDriver getRemoteDriver() {
        return null;
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

//    private WebDriver getRemoteDriver() {
//        String stringUrl = webConfiguration.getRemoteUrl();
//        Duration readTimeOut = Duration.ofSeconds(10);
//        try {
//            URL url = new URL(stringUrl);
//            ClientConfig config = ClientConfig.defaultConfig().baseUrl(url).readTimeout(readTimeOut);
//            var executor = new HttpCommandExecutor(config);
//            RemoteWebDriver driver = new RemoteWebDriver(executor, getRemoteOptions());
//            driver.setFileDetector(new LocalFileDetector());
//            return driver;
//        } catch (MalformedURLException e) {
//            throw new CommandLine.InitializationException("Unable to create remote driver", e);
//        }
//        return driver;
//    }
//
//    private AbstractDriverOptions<?> getRemoteOptions() {
//        AbstractDriverOptions<?> driverOptions = getSpecificRemoteDriverOptions();
//
//        var browserVersion = webConfiguration.getBrowserVersion();
//        var capabilities = webConfiguration.getRemoteCapabilities();
//
//        driverOptions.setBrowserVersion(browserVersion);
//
//        driverOptions.setCapability("sauce:options", capabilities);
//
//        return driverOptions;
//    }

}
