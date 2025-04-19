package core.web.pageObjects;

import core.config.PropertiesHolder;
import core.driver.WebDriverHolder;
import core.utils.BrowserActions;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class);


    public abstract String basePageUrl();
    public abstract String pathUrl();
    public String uiEngine = PropertiesHolder.getInstance().getConfigProperties().engine();
    public final boolean useSelenide = "selenide".equalsIgnoreCase(uiEngine);

    protected WebDriver driver;
    BrowserActions browserActions = new BrowserActions();

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public BasePage openPage(String... params) {
        String fullUrl = String.format(basePageUrl() + pathUrl(), params);
        if (useSelenide) {
            LOGGER.info("Open new page via Selenide");
            com.codeborne.selenide.Selenide.open(fullUrl);
        } else {
            LOGGER.info("Open new page via Selenium");
            WebDriverHolder.getInstance().getWebDriver().get(fullUrl);
        }
        return this;
    }
}