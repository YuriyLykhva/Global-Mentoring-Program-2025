package core.web;

import core.config.ConfigReader;
import core.driver.RunType;
import core.driver.WebConfiguration;
import core.driver.WebDriverHolder;
import core.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected WebConfiguration webConfiguration;
//    protected User user;

    public BaseTest() {
        this.webConfiguration = new WebConfiguration();
//        this.user = User.createUser();
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
//        user.setLogin(ConfigReader.getProperty("login"));
//        user.setPassword(ConfigReader.getProperty("password"));
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