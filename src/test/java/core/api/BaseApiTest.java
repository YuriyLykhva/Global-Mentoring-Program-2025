package core.api;

import core.driver.RunType;
import core.web.BaseTest;

public class BaseApiTest extends BaseTest {

    String projectName = System.getProperty("projectName");

    final String homePageURL = webConfiguration.getRunType() == RunType.LOCAL ?
            webConfiguration.getLocalUrl() :
            webConfiguration.getRemoteUrl();

    protected static final String BASE_URL = "/api/";

    String token = System.getenv("TOKEN");

}
