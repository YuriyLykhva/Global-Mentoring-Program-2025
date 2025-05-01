package core.driver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ConcurrentModificationException;
import java.util.Objects;

public class WebDriverHolder {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverHolder.class);

    private final static ThreadLocal<WebDriver> webDriver = new InheritableThreadLocal<>();
    private final static ThreadLocal<FluentWait<WebDriver>> webDriverWait = new InheritableThreadLocal<>();
    private static WebDriverHolder instance;
    private WebConfiguration webConfiguration;

    private WebDriverHolder() {
    }

    public static WebDriverHolder getInstance() {
        if (instance == null) {
            synchronized (WebDriverHolder.class) {
                if (instance == null) {
                    instance = new WebDriverHolder();
                }
            }
        }
        return instance;
    }

    public void setDriver(WebConfiguration webConfiguration) {
        this.webConfiguration = webConfiguration;
        if (isRemoteRun()) {
            LOGGER.info("Remote WebDriver execution enabled. Using remote strategy.");
        } else {
            LOGGER.info("Local WebDriver execution enabled. Using local strategy.");
        }

        WebDriver driver = DriverMapping.getDriverStrategy(webConfiguration).getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        webDriver.set(driver);

        FluentWait<WebDriver> wait = new FluentWait<>(webDriver.get())
                .withTimeout(Duration.ofSeconds(webConfiguration.getTimeOutSeconds()))
                .pollingEvery(Duration.ofMillis(webConfiguration.getPollingTimeOutMilliSeconds()))
                .ignoring(NoSuchElementException.class)
                .ignoring(IndexOutOfBoundsException.class)
                .ignoring(NullPointerException.class)
                .ignoring(ConcurrentModificationException.class)
                .ignoring(ClassCastException.class)
                .ignoring(MoveTargetOutOfBoundsException.class)
                .ignoring(StaleElementReferenceException.class);
        webDriverWait.set(wait);
    }

    public WebDriver getWebDriver() {
        return webDriver.get();
    }

    public JavascriptExecutor getJsExecutor() {
        return (JavascriptExecutor) webDriver.get();
    }

    public SessionId getSessionId() {
        return ((RemoteWebDriver) getWebDriver()).getSessionId();
    }

    public boolean isRemoteRun() {
        if (Objects.isNull(webConfiguration)) {
            return false;
        }
        return RunType.REMOTE == webConfiguration.getRunType();
    }

    public FluentWait<WebDriver> getWebDriverWait() {
        return webDriverWait.get();
    }

    public void tearDown() {
        if (getWebDriver() != null) {
            getWebDriver().quit();
            webDriver.remove();
        }
    }

}
