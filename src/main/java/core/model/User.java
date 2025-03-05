package core.model;

import lombok.Data;

import static core.model.UserCreds.getTestData;

@Data
public class User {
    private String login;
    private String password;

    public static User createUser() {
        return new User(getTestData("login"), getTestData("password"));
    }

    private User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}