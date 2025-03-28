package stepDefinitions;

import core.api.ReportPortalApiClient;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.response.Response;

import java.util.Optional;

public class Hooks {

    protected static Response response;
    protected static final ReportPortalApiClient reportPortalApiClient = new ReportPortalApiClient();
    protected static final ThreadLocal<String> createdDashboard = new ThreadLocal<>();

    @Before
    public void setUp() {
        createdDashboard.remove();
    }

    @After
    public void tearDown() {
        if(createdDashboard.get() != null){
            reportPortalApiClient.deleteDashboardById(createdDashboard.get());
        }
    }

    protected static void extractAndSetCreatedDashboard(Response response){
        Optional.ofNullable(response.path("id")).ifPresent(id -> {
            createdDashboard.set(id.toString());
        });
    }
}