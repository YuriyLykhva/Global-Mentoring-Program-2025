package core.web.pageObjects;

import core.driver.WebDriverHolder;
import core.utils.BrowserActions;
import org.openqa.selenium.WebDriver;

public abstract class BasePage {

    public abstract String basePageUrl();
    public abstract String pathUrl();

    protected WebDriver driver;
    BrowserActions browserActions = new BrowserActions();

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public BasePage openPage(String... params) {
        WebDriverHolder.getInstance().getWebDriver().get(String.format(basePageUrl() + pathUrl(), params));
        return this;
    }
}