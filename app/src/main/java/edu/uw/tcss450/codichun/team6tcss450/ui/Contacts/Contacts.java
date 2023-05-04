package edu.uw.tcss450.codichun.team6tcss450.ui.Contacts;

import java.io.Serializable;
import java.net.URLEncoder;

public class Contacts implements Serializable {


    private final String mFirstName;
    private final String mMiddleInitial;
    private final String mLastName;
    private final String mUsername;
    private final int mUserID;
    //possibly implement an "isActive" boolean

    public static class ContactsBuilder {
        private final String mFirstName;
        private final String mMiddleInitial;
        private final String mLastName;
        private final String mUsername;
        private final int mUserID;

        public ContactsBuilder(String theFirstName, String theMiddleInitial, String theLastName, String theUsername, int theUserID) {
            this.mFirstName = theFirstName;
            this.mLastName = theLastName;
            this.mMiddleInitial = theMiddleInitial;
            this.mUsername = theUsername;
            this.mUserID = theUserID;
        }

        public Contacts build() {
            return new Contacts(this);
        }
    }

    public Contacts(final ContactsBuilder build) {
        this.mFirstName = build.mFirstName;
        this.mLastName = build.mLastName;
        this.mMiddleInitial = build.mMiddleInitial;
        this.mUsername = build.mUsername;
        this.mUserID = build.mUserID;
    }

    public String getmFirstName() {
        return  mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public String getmMiddleInitial() {
        return mMiddleInitial;
    }

    public String getmUsername() {
        return mUsername;
    }

    public int getmUserID() {
        return mUserID;
    }
}
