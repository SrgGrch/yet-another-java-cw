package newsline.ui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import newsline.PropertiesApi;
import newsline.model.User;
import newsline.network.RetrofitProvider;
import newsline.network.api.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class SignInController implements Initializable {
    public TextField signIn_login;
    public PasswordField signIn_password;
    public Button signIn_auth;
    public Button signIn_reg;

    private String pass = "";
    private String login = "";

    UserApi api = RetrofitProvider.getRetrofit().create(UserApi.class);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signIn_login.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isBlank())
                login = newValue;
        });

        signIn_password.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isBlank())
                pass = newValue;
        });

        signIn_auth.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> authUser());

        signIn_reg.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            openRegWindow();
        });
    }

    private void authUser() {
        if (login.isBlank() || pass.isBlank()) {
            //TODO Dialog
        } else {
            api.authUser(login, pass).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {


                    switch (response.code()) {
                        case 200: {
                            PropertiesApi.saveUser(response.body());
                            closeWindow();
                            break;
                        }
                        case 401: {
                            System.out.println("NO");
//                            showErrorDialog();
                            break;
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        }
    }

    private void closeWindow() {
        Platform.runLater(() -> {
            Stage authStage = (Stage) signIn_auth.getScene().getWindow();
            NewsFeedController.checkItems();
            authStage.close();
        });
    }

    private void openRegWindow() {
        Platform.runLater(() -> {
            try {
                URL url = Paths.get("src/main/resources/fxml/signup.fxml").toUri().toURL();

                FXMLLoader loader = new FXMLLoader(url);
                Stage stage = new Stage();

                Parent root = loader.load();
                stage.setTitle("Registration");
                stage.setScene(new Scene(root, 300, 400));
                stage.setResizable(false);
                Stage authStage = (Stage) signIn_auth.getScene().getWindow();
                authStage.close();
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }
}
