package stepDefinitions.hooks;

import core.config.ConfigProperties;
import core.config.PropertiesHolder;
import core.driver.RunType;
import core.driver.WebConfiguration;
import core.driver.WebDriverHolder;
import core.utils.BrowserActions;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class WebHooks {

    private final WebConfiguration webConfiguration;
    protected final BrowserActions browserActions;
    protected static final ThreadLocal<String> createdDashboard = new ThreadLocal<>();

    public WebHooks() {
        this.webConfiguration = new WebConfiguration();
        this.browserActions = new BrowserActions();
        loadConfigurationFromFile();
    }

    private void loadConfigurationFromFile() {
        ConfigProperties config = PropertiesHolder.getInstance().getConfigProperties();
        webConfiguration.setBrowserName(config.browser());
        webConfiguration.setToken(config.apiKey());
        webConfiguration.setRunType(RunType.valueOf(config.runType()));
        webConfiguration.setBrowserVersion(config.browserVersion());
        webConfiguration.setLocalUrl(config.rpUrl());
        webConfiguration.setRemoteUrl(config.webDriverRemoteUrl());
        webConfiguration.setTimeOutSeconds(config.timeOutSeconds());
        webConfiguration.setPollingTimeOutMilliSeconds(config.pollingTimeOutMilliSeconds());
        webConfiguration.setReadTimeOutSeconds(config.readTimeOutSeconds());
    }

    @Before("@WEB")
    public void setUp() {
        WebDriverHolder.getInstance().setDriver(webConfiguration);
        createdDashboard.remove();
    }

    @After("@WEB")
    public void tearDown() {
        WebDriverHolder.getInstance().tearDown();
    }

}