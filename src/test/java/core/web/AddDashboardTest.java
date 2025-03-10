package core.web;

import com.github.javafaker.Faker;
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

public class AddDashboardTest extends BaseTest {

    @Test
    @org.testng.annotations.Test
    public void addDashboardTest() {
        final String homePageURL = webConfiguration.getRunType() == RunType.LOCAL ?
                webConfiguration.getLocalUrl() :
                webConfiguration.getRemoteUrl();
        User user = User.createUser();
        LoginPage loginPage = new LoginPage(WebDriverHolder.getInstance().getWebDriver());
        DashboardPage dashboardPage = new DashboardPage(WebDriverHolder.getInstance().getWebDriver());

        String targetDashboardName = new Faker().color().name();

        loginPage
                .openPage(homePageURL)
                .typeLogin(user.getLogin())
                .typePassword(user.getPassword())
                .clickLoginButton();

        List<WebElement> dashboardsBeforeAddingNew = dashboardPage.getDasboardsList();


        List<WebElement> dashboardsAfterAddingNew =
                dashboardPage
                        .clickAddDashboardButton()
                        .typeDashboardName(targetDashboardName)
                        .clickCreateButton()
                        .returnToDashboardPage()
                        .getDasboardsList();

        Assert.assertEquals(dashboardsAfterAddingNew.size(),
                dashboardsBeforeAddingNew.size() + 1,
                "Dashboard is not created!");
        Assertions.assertEquals(dashboardsAfterAddingNew.size(),
                dashboardsBeforeAddingNew.size() + 1,
                "Dashboard is not created!");

        boolean isDashboardCreated = dashboardsAfterAddingNew.stream()
                .map(WebElement::getText)
                .anyMatch(dashboardName -> dashboardName.contains(targetDashboardName));

        Assert.assertTrue(isDashboardCreated, "The new dashboard was not found in the list!");
        Assertions.assertTrue(isDashboardCreated, "The new dashboard was not found in the list!");

    }
}
