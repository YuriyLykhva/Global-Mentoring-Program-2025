package core.web.pageObjects;

import core.util.WaiterWrapperClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignInPage extends BasePage {

    public SignInPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    private static final String SIGNINPAGE_URL = "http://localhost:8080";

    @FindBy(xpath = "//*[@placeholder=\"Login\"]")
    private WebElement loginField;

    @FindBy(xpath = "//*[@placeholder=\"Password\"]")
    private WebElement passwordField;

    @FindBy(xpath = "//*[text()=\"Login\"]")
    private WebElement loginButton;

    @FindBy(xpath = "//*[text()=\"All Dashboards\"]")
    private WebElement allDashboardsTitle;

    @Override
    public SignInPage openPage() {
        String path = "/ui/#login";
        driver.get(SIGNINPAGE_URL + path);
        WaiterWrapperClass.waitForElement(driver, loginField);
        return this;
    }

    public SignInPage typeLogin(String login) {
        loginField.sendKeys(login);
        return this;
    }

    public SignInPage typePassword(String password) {
        passwordField.sendKeys(password);
        return this;
    }

    public SignInPage clickLoginButton() {
        loginButton.click();
        return this;
    }

    public String getAllDashboardsTitle() {
        WaiterWrapperClass.waitForElement(driver, allDashboardsTitle);
        return allDashboardsTitle.getText();
    }

}