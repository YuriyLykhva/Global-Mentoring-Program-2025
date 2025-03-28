package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/",
        glue = {"stepDefinitions", "stepDefinitions.hooks"},
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        tags = "@DeleteDashboard"
)
public class DashboardTestRunner extends AbstractTestNGCucumberTests {
}
