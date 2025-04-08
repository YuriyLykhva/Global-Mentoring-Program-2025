package stepDefinitions.hooks;

import core.api.RestAssured.ReportPortalApiClient;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.response.Response;
import java.util.Optional;

public class ApiHooks {

    public static Response response;
    public static final ReportPortalApiClient reportPortalApiClient = new ReportPortalApiClient();
    public static final ThreadLocal<String> createdDashboard = new ThreadLocal<>();

    @Before("@API")
    public void setUp() {
        createdDashboard.remove();
    }

    @After("@API")
    public void tearDown() {
        if(createdDashboard.get() != null){
            reportPortalApiClient.deleteDashboardById(createdDashboard.get());
        }
    }

    public static void extractAndSetCreatedDashboard(Response response){
        Optional.ofNullable(response.path("id")).ifPresent(id -> createdDashboard.set(id.toString()));
    }
}