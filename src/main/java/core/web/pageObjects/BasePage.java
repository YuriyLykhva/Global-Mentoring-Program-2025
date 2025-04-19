package core.web.pageObjects;

import core.driver.WebDriverHolder;
import core.utils.BrowserActions;
import org.openqa.selenium.WebDriver;

public abstract class BasePage {

    public abstract String basePageUrl();
    public abstract String pathUrl();
    public final boolean useSelenide = true;

    protected WebDriver driver;
    BrowserActions browserActions = new BrowserActions();

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public BasePage openPage(String... params) {
        String fullUrl = String.format(basePageUrl() + pathUrl(), params);
        if (useSelenide) {
            com.codeborne.selenide.Selenide.open(fullUrl);
        } else {
            WebDriverHolder.getInstance().getWebDriver().get(fullUrl);
        }
        return this;
    }
}