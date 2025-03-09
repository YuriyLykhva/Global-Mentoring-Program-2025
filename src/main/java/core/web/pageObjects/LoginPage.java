package core.web.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static core.utils.UiWait.waitForElementLocatedBy;

public class LoginPage extends BasePage {

    private final String loginInputLocator = "//input[@name='login']";
    private final String passwordInputLocator = "//input[@name='password']";
    private final By loginButton = By.cssSelector("button[type='submit']");

    private static final String LOGIN_PATH = "/ui/#login";

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    public LoginPage openPage(String homePageURL) {
        driver.get(homePageURL + LOGIN_PATH);
        waitForElementLocatedBy(driver, loginButton);
        return this;
    }

    public LoginPage typeLogin(String login) {
        browserActions.inputText(By.xpath(loginInputLocator), login);
        return this;
    }

    public LoginPage typePassword(String password) {
        browserActions.inputText(By.xpath(passwordInputLocator), password);
        return this;
    }

    public LoginPage clickLoginButton() {
        browserActions.click(loginButton);
        return this;
    }

}