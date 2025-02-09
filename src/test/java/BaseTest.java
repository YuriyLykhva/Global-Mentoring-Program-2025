import core.driver.BrowserEnum;
import core.driver.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import static core.driver.WebDriverFactory.quitDriver;

public class BaseTest {

    protected WebDriver driver;

    @BeforeClass
    @Parameters("browser")
    public void setup(@Optional("CHROME") BrowserEnum browser) {
        driver = WebDriverFactory.getDriver(browser);
    }

    @AfterMethod
    public void clearCookie() {
        driver.manage().deleteAllCookies();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        quitDriver();
    }
}