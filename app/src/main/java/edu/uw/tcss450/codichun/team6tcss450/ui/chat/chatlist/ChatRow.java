package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatlist;

/**
 * An object for a row of the chat list
 * @author codichun
 * @version 1.0
 */
public class ChatRow {

    String name;
    String message;
    int profile;


    int ChatRoomId;


    public ChatRow(String name, String message, int image, int ChatRoomID) {
        this.name = name;
        this.message = message;
        this.profile = image;
        this.ChatRoomId = ChatRoomID;
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



    public int getChatRoomId() {
        return ChatRoomId;
    }



    public void setProfile(int profile) {
        this.profile = profile;
    }

    public void setChatRoomId(int chatRoomId) {
        ChatRoomId = chatRoomId;
    }
}
