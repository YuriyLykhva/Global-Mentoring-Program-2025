package stepDefinitions;

import core.utils.RandomStringGenerator;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class DashboardSteps {

    @Given("User has API access with a valid token")
    public void userIsAuthorized() {
        System.out.println("User has valid API");
    }

    @When("User creates a new dashboard with a random name")
    public void createANewDashboardWithRandomName() {
        String targetDashboardName = RandomStringGenerator.getTargetDashboardName();
        Hooks.response = Hooks.reportPortalApiClient.createDashboardWithName(targetDashboardName);
        Hooks.extractAndSetCreatedDashboard(Hooks.response);
    }

    @Then("the dashboard is created successfully {int} times")
    public void theDashboardIsCreatedSuccessfully(int i) {
        System.out.println("Dashboard is created successfully " + i + " times");
        Assert.assertEquals(Hooks.response.getStatusCode(), 201);
    }

    @And("User deletes the dashboard")
    public void userDeletesTheDashboard() {
        String createdId = Hooks.createdDashboard.get();
        Hooks.response = Hooks.reportPortalApiClient.deleteDashboardById(createdId);
    }

    @Then("the dashboard is deleted successfully {int} times")
    public void theDashboardIsDeletedSuccessfully(int i) {
        System.out.println("Dashboard is deleted successfully " + i + " times");
        Assert.assertEquals(Hooks.response.getStatusCode(), 200);
    }

}
