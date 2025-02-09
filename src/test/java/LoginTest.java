import core.model.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.SignInPage;

public class LoginTest extends BaseTest {

    @Test
    public void loginViaUserRole() {
        User user = User.createUser();
        String allDashboardsTitle = new SignInPage(driver)
                .openPage()
                .typeLogin(user.getLogin())
                .typePassword(user.getPassword())
                .clickLoginButton()
                .getAllDashboardsTitle();

        Assert.assertEquals(allDashboardsTitle, "ALL DASHBOARDS");
    }
}