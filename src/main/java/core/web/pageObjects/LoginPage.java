package core.web.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static core.utils.UiWait.waitForElementLocatedBy;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class LoginPage extends BaseReportPortalPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);


    private final String loginInputLocator = "//input[@name='login']";
    private final String passwordInputLocator = "//input[@name='password']";
    private final By loginButton = By.cssSelector("button[type='submit']");

    private static final String LOGIN_PATH = "/ui/#login";

    public LoginPage(WebDriver driver) {
        super(driver);
        if(!useSelenide) {
            PageFactory.initElements(driver, this);
        }
    }

    @Override
    public String pathUrl() {
        return LOGIN_PATH;
    }

    @Override
    public LoginPage openPage(String... params) {
        super.openPage(params);
        if (useSelenide) {
            $(loginButton).shouldBe(visible);
        } else {
            waitForElementLocatedBy(driver, loginButton);
        }
        return this;
    }

    public LoginPage typeLogin(String login) {
        if (useSelenide) {
            $x(loginInputLocator).setValue(login);
        } else {
            browserActions.inputText(By.xpath(loginInputLocator), login);
        }
        LOGGER.info("Login is entered");
        return this;
    }

    public LoginPage typePassword(String password) {
        if (useSelenide) {
            $x(passwordInputLocator).setValue(password);
        } else {
            browserActions.inputText(By.xpath(passwordInputLocator), password);
        }
        LOGGER.info("Password is entered");
        return this;
    }

    public LoginPage clickLoginButton() {
        if (useSelenide) {
            $(loginButton).click();
        } else {
            browserActions.click(loginButton);
        }
        LOGGER.info("Clicked login button");
        return this;
    }

}