package newsline;

import com.google.gson.Gson;
import newsline.model.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


/**
 * Api for application Properties
 */

public class PropertiesApi {

    /**
     * Saves authUser to .prop file
     * @param authUser authUser
     */

    public static void saveUser(User authUser) {
        Properties props = new Properties();

        Gson gson = new Gson();
        String json = gson.toJson(authUser, User.class);

        try {
            props.load(new FileInputStream("config.prop"));
            props.put("authUser", json);
            props.store(new FileOutputStream("config.prop"), " ");
        } catch (IOException e) {
            props.put("authUser", json);
            try {
                props.put("authUser", json);
                props.store(new FileOutputStream("config.prop"), " ");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * @return user, if {@code null} - user not authorised
     */

    public static User getUser() {
        Properties props = new Properties();

        Gson gson = new Gson();
        String json = null;

        try {
            props.load(new FileInputStream("config.prop"));
            json = (String) props.get("authUser");
        } catch (IOException e) {
            try {
                props.store(new FileOutputStream("config.prop"), " ");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

        if (json != null)
            return gson.fromJson(json, User.class);
        else return null;
    }

    public static void delData(){
        Properties props = new Properties();

        try {
            props.load(new FileInputStream("config.prop"));
            props.clear();
            props.store(new FileOutputStream("config.prop"), " ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
