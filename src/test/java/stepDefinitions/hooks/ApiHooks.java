package stepDefinitions.hooks;

import core.api.api_client.CustomResponse;
import core.api.ReportPortalApiClient;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.util.Optional;

public class ApiHooks {

    public static CustomResponse response;
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

    public static void extractAndSetCreatedDashboard(CustomResponse response){
        Optional.ofNullable(response.getFiledValueFromJson("id")).ifPresent(id -> createdDashboard.set(id));
    }
}