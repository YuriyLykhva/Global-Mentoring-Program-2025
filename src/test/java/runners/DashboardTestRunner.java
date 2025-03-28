package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/",
        glue = "stepDefinitions",
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        tags = "@API"
)
public class DashboardTestRunner extends AbstractTestNGCucumberTests {
}
