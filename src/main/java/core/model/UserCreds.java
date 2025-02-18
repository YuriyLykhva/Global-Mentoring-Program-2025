package core.model;

import java.util.ResourceBundle;

public class UserCreds {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("admin");

    static String getTestData(String key) {
        String value =  resourceBundle.getString(key);
        value = value.replace("${ADMIN_LOGIN}", System.getenv("LOGIN"));
        value = value.replace("${ADMIN_PASSWORD}", System.getenv("PASSWORD"));
        return value;
    }
}
