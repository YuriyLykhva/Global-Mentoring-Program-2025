package core.web;

import core.utils.TestListener;
import core.config.ConfigProperties;
import core.config.PropertiesHolder;
import core.driver.RunType;
import core.driver.WebConfiguration;
import core.driver.WebDriverHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners({TestListener.class})
public class BaseTest {

    protected WebConfiguration webConfiguration;

    public BaseTest() {
        this.webConfiguration = new WebConfiguration();
        loadConfigurationFromFile();
    }

    private void loadConfigurationFromFile() {
        ConfigProperties config = PropertiesHolder.getInstance().getConfigProperties();
        webConfiguration.setBrowserName(config.browser());
        webConfiguration.setRunType(RunType.valueOf(config.runType()));
        webConfiguration.setBrowserVersion(config.browserVersion());
        webConfiguration.setLocalUrl(config.localUrl());
        webConfiguration.setRemoteUrl(config.remoteUrl());
        webConfiguration.setTimeOutSeconds(config.timeOutSeconds());
        webConfiguration.setPollingTimeOutMilliSeconds(config.pollingTimeOutMilliSeconds());
        webConfiguration.setReadTimeOutSeconds(config.readTimeOutSeconds());
    }

    @BeforeEach
    @BeforeMethod(alwaysRun = true)
    public void beforeEachMethodSetupWeb() {
        WebDriverHolder.getInstance().setDriver(webConfiguration);
    }

    @AfterEach
    @AfterMethod(alwaysRun = true)
    public void afterEachMethodTearDownWeb() {
        WebDriverHolder.getInstance().tearDown();
    }

}