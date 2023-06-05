package edu.uw.tcss450.codichun.team6tcss450.ui.contacts;

import java.io.Serializable;

public class Contact implements Serializable {
    private final String mFirstName;
    private final String mLastName;
    private final String mUserName;
    private final String mEmail;
    private final String mUserID;
    private final Status mStatus;


    public Contact(String firstName, String lastName, String userName, String email, String userID, Status status) {
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mUserName = userName;
        this.mEmail = email;
        this.mUserID = userID;
        this.mStatus = status;
    }


    public String getFirstName() {
        return  mFirstName;
    }
    public String getLastName() {
        return mLastName;
    }
    public String getUserName() {
        return mUserName;
    }
    public String getEmail() {
        return mEmail;
    }
    public String getUserID() {
        return mUserID;
    }
    public Status getStatus() {
        return mStatus;
    }
}
