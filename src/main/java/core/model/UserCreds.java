package core.model;

import java.util.ResourceBundle;

public class UserCreds {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("admin");

    static String getTestData(String key) {
        return resourceBundle.getString(key);
    }
}
