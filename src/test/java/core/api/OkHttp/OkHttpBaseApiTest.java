package core.api.OkHttp;

import core.BaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static core.api.OkHttp.ThreadLocalHolder.createdDashboard;

public class OkHttpBaseApiTest extends BaseTest {
    protected final OkHttpReportPortalApiClient okHttpReportPortalApiClient = new OkHttpReportPortalApiClient();

    @BeforeEach
    @BeforeMethod(alwaysRun = true)
    public void setup() {
        createdDashboard.remove();
    }

    @AfterEach
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        String idToDelete = createdDashboard.get();
        if (idToDelete != null) {
            okHttpReportPortalApiClient.deleteDashboardById(idToDelete);
        }
    }


}
