package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatroom;

/**
 * A item of the recycler view on the room information page
 * @author codichun
 * @version 1.0
 */
public class RoomInfoMember {


    String username;
    int memberId;

    String email;


    /**
     * Constructor
     * @param username
     * @param memberId
     * @param email
     */
    public RoomInfoMember(String username, int memberId, String email) {
        this.username = username;
        this.memberId = memberId;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
