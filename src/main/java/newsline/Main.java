package newsline;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import newsline.model.User;

import java.net.URL;
import java.nio.file.Paths;


public class Main extends Application {

    static public Stage s;

    @Override
    public void start(Stage primaryStage) throws Exception {

        s = primaryStage;

        URL url = Paths.get("src/main/resources/fxml/newsfeed.fxml").toUri().toURL();

        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Newsline");
        primaryStage.setScene(new Scene(root, 850, 600));

        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
