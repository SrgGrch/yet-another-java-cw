 package newsline.model;

import java.util.ArrayList;

public class Article {
    private String id;
    private String title;
    private String subtitle;
    private String text;
    private User author;
    private ArrayList<Comment> comments;

    public Article(String id, String title, String subtitle, String text, User author, ArrayList<Comment> comments) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.text = text;
        this.author = author;
        this.comments = comments;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getText() {
        return text;
    }

    public User getAuthor() {
        return author;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }
}
