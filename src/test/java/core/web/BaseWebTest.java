package core.web;

import core.BaseTest;
import core.config.ConfigProperties;
import core.config.PropertiesHolder;
import core.driver.RunType;
import core.driver.WebConfiguration;
import core.driver.WebDriverHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseWebTest extends BaseTest {
    private final WebConfiguration webConfiguration;

    public BaseWebTest() {
        this.webConfiguration = new WebConfiguration();
        loadConfigurationFromFile();
    }

    private void loadConfigurationFromFile() {
        ConfigProperties config = PropertiesHolder.getInstance().getConfigProperties();
        webConfiguration.setBrowserName(config.browser());
        webConfiguration.setToken(config.token());
        webConfiguration.setRunType(RunType.valueOf(config.runType()));
        webConfiguration.setBrowserVersion(config.browserVersion());
        webConfiguration.setLocalUrl(config.webUrl());
        webConfiguration.setRemoteUrl(config.webDriverRemoteUrl());
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
