package core.api;

import core.BaseTest;
import core.api.api_client.CustomResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.Optional;

public class BaseApiTest extends BaseTest {

    protected final ReportPortalApiClient reportPortalApiClient = new ReportPortalApiClient();
    protected final ThreadLocal<String> createdDashboard = new ThreadLocal<>();

    @BeforeEach
    @BeforeMethod(alwaysRun = true)
    public void setup() {
        createdDashboard.remove();
    }

    @AfterEach
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if(createdDashboard.get() != null){
            reportPortalApiClient.deleteDashboardById(createdDashboard.get());
        }
    }

    protected void extractAndSetCreatedDashboard(CustomResponse response){
        Optional.ofNullable(response.getFiledValueFromJson("id")).ifPresent(createdDashboard::set);
    }

}
