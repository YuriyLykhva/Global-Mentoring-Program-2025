package core.web;

import core.driver.RunType;
import core.driver.WebDriverHolder;
import core.model.User;
import core.web.pageObjects.DashboardPage;
import core.web.pageObjects.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class DeleteDashboardTest extends BaseTest {

    @Test
    @org.testng.annotations.Test
    public void deleteFirstDashboardTest() {
        final String homePageURL = webConfiguration.getRunType() == RunType.LOCAL ?
                webConfiguration.getLocalUrl() :
                webConfiguration.getRemoteUrl();
        User user = User.createUser();
        LoginPage loginPage = new LoginPage(WebDriverHolder.getInstance().getWebDriver());
        DashboardPage dashboardPage = new DashboardPage(WebDriverHolder.getInstance().getWebDriver());

        loginPage
                .openPage(homePageURL)
                .typeLogin(user.getLogin())
                .typePassword(user.getPassword())
                .clickLoginButton();

        List<WebElement> dashboardsBeforeDeleting = dashboardPage.getDasboardsList();

        List<WebElement> dashboardsAfterDeleting = dashboardPage
                .clickDeleteFirstDashboardButton()
                .clickConfirmDeleteButton()
                .returnToDashboardPage()
                .getDasboardsList();

        Assert.assertEquals(dashboardsAfterDeleting.size(),
                dashboardsBeforeDeleting.size() - 1, "Dashboard is not deleted!");

        Assertions.assertEquals(dashboardsAfterDeleting.size(),
                dashboardsBeforeDeleting.size() - 1, "Dashboard is not deleted!");

    }
}