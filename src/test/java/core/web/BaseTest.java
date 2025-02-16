package core.web;

import core.driver.RunType;
import core.driver.WebConfiguration;
import core.driver.WebDriverHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected WebConfiguration webConfiguration;

    public BaseTest() {
        this.webConfiguration = new WebConfiguration();
        configureWebSettings();
    }

    private void configureWebSettings() {
        webConfiguration.setBrowserName("chrome");
        webConfiguration.setRunType(RunType.LOCAL);
        webConfiguration.setBrowserVersion("latest");
        webConfiguration.setRemoteUrl("");
        webConfiguration.setTimeOutSeconds(10L);
        webConfiguration.setPollingTimeOutMilliSeconds(500L);
        webConfiguration.setReadTimeOutSeconds(15L);
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeEachMethodSetupWeb() {
        WebDriverHolder.getInstance().setDriver(webConfiguration);
    }

    @AfterMethod(alwaysRun = true)
    public void afterEachMethodTearDownWeb() {
        WebDriverHolder.getInstance().tearDown();
    }

}