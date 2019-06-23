package newsline.model;

public class Comment {
    private String text;
    private User author;


    public Comment(String text, User author) {
        this.text = text;
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public User getAuthor() {
        return author;
    }
}
