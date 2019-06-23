package newsline.ui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import newsline.PropertiesApi;
import newsline.model.Article;
import newsline.network.RetrofitProvider;
import newsline.network.api.NewsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NewsFeedController implements Initializable {

    public ListView<Article> newsline_list;
    public BorderPane newsline_root;

    private static MenuItem logInItem = new MenuItem("Log in");
    private static MenuItem logOutItem = new MenuItem("Log out");

    private NewsApi api = RetrofitProvider.getRetrofit().create(NewsApi.class);

    private ObservableList<Article> gamesObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initMenu();

        newsline_list.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            try {
                openArticleScreen(newsline_list.getSelectionModel().getSelectedIndex());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        getNews();




    }

    private void openArticleScreen(int selectedIndex) throws IOException {
        URL url = Paths.get("src/main/resources/fxml/articleScreen.fxml").toUri().toURL();

        FXMLLoader loader = new FXMLLoader(url);//(getClass().getResource("game_screen.fxml"));
        Stage stage = new Stage();
        Parent root = loader.load();

        ArticleScreenController controller = loader.getController();
        controller.initData(gamesObservableList.get(selectedIndex));

        stage.setTitle(gamesObservableList.get(selectedIndex).getTitle());
        stage.setScene(new Scene(root, 600, 800));
        stage.setResizable(false);
        stage.show();
    }

    private void getNews() {
        api.getNewsList().enqueue(new Callback<ArrayList<Article>>() {
            @Override
            public void onResponse(Call<ArrayList<Article>> call, Response<ArrayList<Article>> response) {
                if (response.body() != null)
                    onNewsReady(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Article>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void onNewsReady(ArrayList<Article> list) {
        Platform.runLater(() -> {
            newsline_root.getScene().getWindow().setOnCloseRequest(e -> {
                Platform.exit();
                System.exit(0);
            });
            gamesObservableList.clear();
            gamesObservableList.addAll(list);
            newsline_list.setItems(gamesObservableList);
            newsline_list.setCellFactory(param -> new ArticleItemController());
        });
    }


    private void initMenu() {
        MenuBar menuBar = new MenuBar();

        Menu accountMenu = new Menu();
        Label acc = new Label("Account");
        accountMenu.setGraphic(acc);

        logOutItem.setOnAction(e -> logOut());
        logInItem.setOnAction(e -> authUser());
        accountMenu.getItems().addAll(logOutItem, logInItem);

        checkItems();

        menuBar.getMenus().addAll(accountMenu);

        newsline_root.setTop(menuBar);
    }

    static void checkItems() {

        if (PropertiesApi.getUser() != null) {
            logOutItem.setVisible(true);
            logInItem.setVisible(false);
        } else {
            logOutItem.setVisible(false);
            logInItem.setVisible(true);
        }
    }

    private void authUser() {
        try {
            URL url = Paths.get("src/main/resources/fxml/signin.fxml").toUri().toURL();

            FXMLLoader loader = new FXMLLoader(url);
            Stage stage = new Stage();

            Parent root = loader.load();
            stage.setTitle("Auth");
            stage.setScene(new Scene(root, 300, 400));
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logOut() {
        PropertiesApi.delData();
        checkItems();
    }
}
