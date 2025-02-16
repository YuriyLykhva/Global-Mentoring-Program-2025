package core.web;

import core.driver.WebDriverHolder;
import core.model.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import core.web.pageObjects.SignInPage;

public class LoginTest extends BaseTest {

    @Test
    public void loginViaUserRole() {
        logger.info("Starting loginViaUserRole test...");

        User user = User.createUser();
        SignInPage signInPage = new SignInPage(WebDriverHolder.getInstance().getWebDriver());

        String allDashboardsTitle = signInPage
                .openPage()
                .typeLogin(user.getLogin())
                .typePassword(user.getPassword())
                .clickLoginButton()
                .getAllDashboardsTitle();

        Assert.assertEquals(allDashboardsTitle, "ALL DASHBOARDS", "User is not logged in!");
        logger.info("loginViaUserRole test passed successfully!");

    }
}