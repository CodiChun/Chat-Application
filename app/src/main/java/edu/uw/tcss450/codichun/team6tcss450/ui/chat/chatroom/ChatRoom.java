package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatroom;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulate chat message details.
 */
public final class ChatRoom implements Serializable {

    private int mChatRoomID;
    private String mRoomName;



    private List<Integer> mMemberID;

    public ChatRoom(String theRoomName, List<Integer> theMemberID, int theChatRoomID) {
        mRoomName = theRoomName;
        mMemberID = theMemberID;
        mChatRoomID = theChatRoomID;
    }

    /**
     * Static factory method to turn a properly formatted JSON String into a
     * ChatMessage object.
     * @param cmAsJson the String to be parsed into a ChatMessage Object.
     * @return a ChatMessage Object with the details contained in the JSON String.
     * @throws JSONException when cmAsString cannot be parsed into a ChatMessage.
     */
    public static ChatRoom createFromJsonString(final String cmAsJson) throws JSONException {
        final JSONObject cr = new JSONObject(cmAsJson);
        final JSONArray membersArray = cr.getJSONArray("memberId");
        List<Integer> membersList = new ArrayList<>();

        for (int i = 0; i < membersArray.length(); i++) {
            membersList.add(membersArray.getInt(i));
        }

        return new ChatRoom(cr.getString("chatRoomName"), membersList, cr.getInt("chatId"));
    }


    public String getmRoomName() {
        return mRoomName;
    }

    public List<Integer> getmMemberID() {
        return mMemberID;
    }

    public int getmChatRoomID() {
        return mChatRoomID;
    }

    public void setChatRoomID(int theID){
        mChatRoomID = theID;
    }


    /**
     * Provides equality solely based on MessageId.
     * @param other the other object to check for equality
     * @return true if other message ID matches this message ID, false otherwise
     */
    @Override
    public boolean equals(@Nullable Object other) {
        boolean result = false;
        if (other instanceof ChatRoom) {
            result = mChatRoomID == ((ChatRoom) other).getmChatRoomID();
        }
        return result;
    }
}
