package core.model;

import core.config.PropertiesHolder;
import lombok.Data;

@Data
public class User {
    private String login;
    private String password;

    public static User createUser() {
        return new User(
                PropertiesHolder.getInstance().getConfigProperties().login(),
                PropertiesHolder.getInstance().getConfigProperties().password());
    }

    private User(String login, String password) {
        this.login = login;
        this.password = password;
    }

}