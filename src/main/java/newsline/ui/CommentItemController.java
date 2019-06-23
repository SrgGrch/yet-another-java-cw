package newsline.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import newsline.model.Article;
import newsline.model.Comment;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class CommentItemController extends ListCell<Comment> {

    @FXML
    private Label item_text;

    @FXML
    private Label item_author;

    @FXML
    private GridPane gridPane;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Comment comment, boolean empty) {
        super.updateItem(comment, empty);
        if(empty || comment == null) {
            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                try {
                    URL url = Paths.get("src/main/resources/fxml/commentItem.fxml").toUri().toURL();
                    mLLoader = new FXMLLoader(url);
                    mLLoader.setController(this);
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            item_text.setText(comment.getText());
            item_author.setText("@" + comment.getAuthor().getLogin());

            setText(null);
            setGraphic(gridPane);
        }

    }
}
