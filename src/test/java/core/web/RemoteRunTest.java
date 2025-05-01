package core.web;

import core.driver.WebDriverHolder;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class RemoteRunTest extends BaseWebTest{
    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteRunTest.class);

    @Test
    public void openLambdaTestSite() {
        WebDriver driver = WebDriverHolder.getInstance().getWebDriver();
        driver.get("https://www.lambdatest.com/");
        LOGGER.info("Title: {}", driver.getTitle());
    }

}

