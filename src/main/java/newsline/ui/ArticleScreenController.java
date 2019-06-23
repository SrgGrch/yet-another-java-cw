package newsline.ui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import newsline.PropertiesApi;
import newsline.model.Article;
import newsline.model.Comment;
import newsline.network.RetrofitProvider;
import newsline.network.api.NewsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ArticleScreenController implements Initializable {

    @FXML
    public Label article_title;
    @FXML
    public Label article_author;
    @FXML
    public Label article_subtitle;
    @FXML
    public Label article_text;
    @FXML
    public ListView<Comment> article_commentList;
    @FXML
    public Button article_newComment;
    private Article article;

    private ObservableList<Comment> gamesObservableList = FXCollections.observableArrayList();

    NewsApi api = RetrofitProvider.getRetrofit().create(NewsApi.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initData(Article article) {
        this.article = article;
        if (article != null) {
            article_title.setText(article.getTitle());
            article_subtitle.setText(article.getSubtitle());
            article_text.setText(article.getText());
            article_author.setText("@" + article.getAuthor().getLogin());

            article_newComment.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> addComment());

            setComments(article.getComments());
        }
    }

    public void updateData(Article article) {
        Platform.runLater(() -> {
            this.article = article;
            if (article != null) {
                article_title.setText(article.getTitle());
                article_subtitle.setText(article.getSubtitle());
                article_text.setText(article.getText());
                article_author.setText("@" + article.getAuthor().getLogin());

                setComments(article.getComments());
            }
        });
    }

    private void addComment() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Новый комментарий");

        ButtonType addButtonType = new ButtonType("Добавить", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, cancelButtonType);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(8, 8, 0, 8));

        TextArea comment = new TextArea();
        comment.setPromptText("Комментарий");

        grid.add(comment, 0, 0);

        Node loginButton = dialog.getDialogPane().lookupButton(addButtonType);
        loginButton.setDisable(true);

        comment.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> comment.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return comment.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(this::sendComment);
    }

    private void sendComment(String comment) {
        api.addComment(article.getId(), new Comment(comment, PropertiesApi.getUser())).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                if (response.body() != null)
                    updateData(response.body());
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private void setComments(ArrayList<Comment> comments) {
        if (comments != null && comments.size() != 0) {
            gamesObservableList.clear();
            gamesObservableList.addAll(comments);
            article_commentList.setItems(gamesObservableList);
            article_commentList.setCellFactory(param -> new CommentItemController());
        }
    }
}
