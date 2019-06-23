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
import newsline.model.RegUser;
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

public class SignUpController implements Initializable {
    public TextField signUp_login;
    public PasswordField signUp_password;
    public PasswordField signUp_passwordRepeat;
    public Button signUp_reg;
    public Button signUp_back;

    private String user_login;
    private String user_pass;
    private String user_passRep;

    UserApi api = RetrofitProvider.getRetrofit().create(UserApi.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signUp_login.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isBlank())
                user_login = newValue;
        });


        signUp_password.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isBlank())
                user_pass = newValue;
        });

        signUp_passwordRepeat.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isBlank())
                user_passRep = newValue;
        });

        signUp_reg.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> regUser());

        signUp_back.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> backToAuthWindow());
    }

    private void backToAuthWindow() {
        Platform.runLater(() -> {
            try {
                URL url = Paths.get("src/main/resources/fxml/signin.fxml").toUri().toURL();

                FXMLLoader loader = new FXMLLoader(url);
                Stage stage = new Stage();

                Parent root = loader.load();
                stage.setTitle("Store");
                stage.setScene(new Scene(root, 300, 400));
                stage.setResizable(false);
                Stage authStage = (Stage) signUp_back.getScene().getWindow();
                authStage.close();
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    private void regUser() {
        if ((!user_login.isBlank() && !user_pass.isBlank()) && user_pass.equals(user_passRep))
            api.regUser(new RegUser(
                user_login,
                user_pass
        )).enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    switch (response.code()) {
                        case 200: {
                            PropertiesApi.saveUser(response.body());
                            closeWindow();
                            break;
                        }
                        case 418: {
                            System.out.println("EXIST");
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

    private void closeWindow() {
        Platform.runLater(() -> {
            Stage authStage = (Stage) signUp_back.getScene().getWindow();
            NewsFeedController.checkItems();
            authStage.close();
        });
    }

    private void showErrorDialog() {

    }
}
