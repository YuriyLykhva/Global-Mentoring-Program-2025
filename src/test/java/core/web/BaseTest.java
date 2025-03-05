package core.web;

import core.config.ConfigReader;
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
        loadConfigurationFromFile();
    }

    private void loadConfigurationFromFile() {
        webConfiguration.setBrowserName(ConfigReader.getProperty("browser"));
        webConfiguration.setRunType(RunType.valueOf(ConfigReader.getProperty("runType")));
        webConfiguration.setBrowserVersion(ConfigReader.getProperty("browserVersion"));
        webConfiguration.setLocalUrl(ConfigReader.getProperty("localUrl"));
        webConfiguration.setRemoteUrl(ConfigReader.getProperty("remoteUrl"));
        webConfiguration.setTimeOutSeconds(ConfigReader.getLongProperty("timeOutSeconds", 10L));
        webConfiguration.setPollingTimeOutMilliSeconds(ConfigReader.getLongProperty("pollingTimeOutMilliSeconds", 500L));
        webConfiguration.setReadTimeOutSeconds(ConfigReader.getLongProperty("readTimeOutSeconds", 15L));
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