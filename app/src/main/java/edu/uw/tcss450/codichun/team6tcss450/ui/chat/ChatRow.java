package edu.uw.tcss450.codichun.team6tcss450.ui.chat;

public class ChatRow {
    String name;
    String message;
    int profile;

    public ChatRow(String name, String message, int image) {
        this.name = name;
        this.message = message;
        this.profile = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getImage() {
        return profile;
    }

    public void setImage(int image) {
        this.profile = image;
    }
}
