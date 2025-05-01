package stepDefinitions;

import core.driver.WebDriverHolder;
import core.model.User;
import core.web.pageObjects.AllDashboardsPage;
import core.web.pageObjects.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class LoginSteps {

    LoginPage loginPage;

    @Given("User opens the login page")
    public void user_opens_the_login_page() {
        loginPage = new LoginPage(WebDriverHolder.getInstance().getWebDriver());
        loginPage.openPage();
    }

    @When("User enters valid credentials")
    public void user_enters_valid_credentials() {
        User user = User.createUser();
        loginPage
                .typeLogin(user.getLogin())
                .typePassword(user.getPassword());
    }

    @When("Clicks the login button")
    public void clicks_the_login_button() {
        loginPage.clickLoginButton();
    }

    @Then("User should see the dashboard page")
    public void user_should_see_the_dashboard_page() {
        AllDashboardsPage dashboardPage = new AllDashboardsPage(WebDriverHolder.getInstance().getWebDriver());
        dashboardPage.openPage();
        String allDashboardsTitle = dashboardPage.getAllDashboardsTitle();
        Assert.assertEquals(allDashboardsTitle, "ALL DASHBOARDS", "User is not logged in!");
    }

}
