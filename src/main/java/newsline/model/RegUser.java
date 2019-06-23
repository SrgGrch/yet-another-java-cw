package newsline.model;

import java.io.Serializable;

public class RegUser implements Serializable {

    private String login;
    private String password;

    public RegUser(String login, String password) {
        this.password = password;
        this.login = login;
    }
}
