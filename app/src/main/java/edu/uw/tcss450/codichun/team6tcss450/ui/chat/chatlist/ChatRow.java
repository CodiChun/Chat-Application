package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatlist;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



/**
 * An object for a row of the chat list
 * @author codichun
 * @version 1.0
 */
public class ChatRow {


    private int mChatRoomID;
    private String mRoomName;



    private List<Integer> mMemberID;
    private int mProfile;
    //private String mLastMessage;

    public ChatRow(String theRoomName, ArrayList<Integer> theMemberID, int theChatRoomID, int theProfile) {
        mRoomName = theRoomName;
        mMemberID = theMemberID;
        mChatRoomID = theChatRoomID;
        mProfile = theProfile;
    }

    public ChatRow(String theRoomName, int theChatRoomID, int theProfile) {
        mRoomName = theRoomName;
        mChatRoomID = theChatRoomID;
        mProfile = theProfile;
    }

    /**
     * Static factory method to turn a properly formatted JSON String into a
     * ChatMessage object.
     * @param cmAsJson the String to be parsed into a ChatMessage Object.
     * @return a ChatMessage Object with the details contained in the JSON String.
     * @throws JSONException when cmAsString cannot be parsed into a ChatMessage.
     */
    public static ChatRow createFromJsonString(final String cmAsJson) throws JSONException {
        final JSONObject cr = new JSONObject(cmAsJson);
        final JSONArray membersArray = cr.getJSONArray("memberId");
        List<Integer> membersList = new ArrayList<>();

        for (int i = 0; i < membersArray.length(); i++) {
            membersList.add(membersArray.getInt(i));
        }

        return new ChatRow(cr.getString("chatRoomName"), (ArrayList<Integer>) membersList, cr.getInt("chatId"), cr.getInt("profile"));
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

    public void setmChatRoomID(int mChatRoomID) {
        this.mChatRoomID = mChatRoomID;
    }

    public void setmRoomName(String mRoomName) {
        this.mRoomName = mRoomName;
    }

    public void setmMemberID(List<Integer> mMemberID) {
        this.mMemberID = mMemberID;
    }

    public int getmProfile() {
        return mProfile;
    }

    public void setmProfile(int mProfile) {
        this.mProfile = mProfile;
    }

    /**
     * Provides equality solely based on MessageId.
     * @param other the other object to check for equality
     * @return true if other message ID matches this message ID, false otherwise
     */
    @Override
    public boolean equals(@Nullable Object other) {
        boolean result = false;
        if (other instanceof ChatRow) {
            result = mChatRoomID == ((ChatRow) other).getmChatRoomID();
        }
        return result;
    }
}
