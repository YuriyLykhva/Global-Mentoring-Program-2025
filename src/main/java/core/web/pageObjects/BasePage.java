package core.web.pageObjects;

import core.driver.WebDriverHolder;
import core.util.BrowserActions;
import org.openqa.selenium.WebDriver;

public abstract class BasePage {

    protected WebDriver driver;
    BrowserActions browserActions = new BrowserActions();

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public BasePage openPage(String targetUrl) {
        WebDriverHolder.getInstance().getWebDriver().get(targetUrl);
        return this;
    }
}