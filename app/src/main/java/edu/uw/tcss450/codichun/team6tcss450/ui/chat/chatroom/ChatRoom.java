package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatroom;

public class ChatRoom {
    String roomName;
    int roomNumber;

    public ChatRoom(String roomName) {
        this.roomName = roomName;
        this.roomNumber = hashCode();
    }
}
