package newsline.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import newsline.model.Article;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class ArticleItemController extends ListCell<Article> {

    @FXML
    private Label item_title;

    @FXML
    private Label item_subtitle;

    @FXML
    private Label item_author;

    @FXML
    private GridPane gridPane;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Article article, boolean empty) {
        super.updateItem(article, empty);

        if(empty || article == null) {
            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                try {
                    URL url = Paths.get("src/main/resources/fxml/articleItem.fxml").toUri().toURL();
                    mLLoader = new FXMLLoader(url);
                    mLLoader.setController(this);
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            item_title.setText(article.getTitle());
            item_subtitle.setText(article.getSubtitle());
            item_author.setText("@" + article.getAuthor().getLogin());

            setText(null);
            setGraphic(gridPane);
        }

    }
}
