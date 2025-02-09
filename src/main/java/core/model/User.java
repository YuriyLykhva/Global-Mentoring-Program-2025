package core.model;

import lombok.Data;
import java.util.ResourceBundle;

@Data
public class User {
    private String login;
    private String password;

    public static User createUser() {
        return new User(getTestData("login"), getTestData("password"));
    }

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("admin");

    private static String getTestData(String key) {
        return resourceBundle.getString(key);
    }

    private User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}