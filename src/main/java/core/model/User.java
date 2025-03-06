package core.model;

import core.config.ConfigReader;
import core.driver.RunType;
import lombok.Data;
import lombok.Setter;

@Data
//@Setter
public class User {
    private String login;
    private String password;

    public static User createUser() {
//        return new User(
//                ConfigReader.getProperty("login"),
//                ConfigReader.getProperty("password"));
        return new User(UserCreds.getTestData("login"), UserCreds.getTestData("password"));
    }

    private User(String login, String password) {
        this.login = login;
        this.password = password;
    }

}