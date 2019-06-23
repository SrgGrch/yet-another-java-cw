package newsline.model;

import java.io.Serializable;

public class User implements Serializable {

    private String id;
    private String login;
    private boolean isRoot;

    public User(String id, String login, boolean isRoot) {
        this.id = id;
        this.login = login;
        this.isRoot = isRoot;
    }

    public String getid() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public boolean isRoot() {
        return isRoot;
    }
}
