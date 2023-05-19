package edu.uw.tcss450.codichun.team6tcss450.ui.contacts;

import java.io.Serializable;

public class Contact implements Serializable {

    private final String mFirstName;
    //private final String mLastName;
    //private final String mUserName;
    //private final String mEmail;
    //private final int mUserID;


    public static class Builder {
        private final String mFirstName;
        //private final String mLastName;
        //private final String mUserName;
        //private final String mEmail;
        //private final int mUserID;

        public Builder(String firstName) {
            this.mFirstName = firstName;
        }

        /*

        public Builder(String firstName, String lastName, String userName, String email, int userID) {
            this.mFirstName = firstName;
            this.mLastName = lastName;
            this.mUserName = userName;
            this.mEmail = email;
            //this.mUserID = userID;
        }

         */

        public Contact build() {
            return new Contact(this);
        }
    }

    private Contact(final Builder builder) {
        this.mFirstName = builder.mFirstName;
        //this.mLastName = builder.mLastName;
        //this.mUserName = builder.mUserName;
        //this.mEmail = builder.mEmail;
        //this.mUserID = builder.mUserID;
    }


    public String getFirstName() {
        return  mFirstName;
    }

    /*
    public String getLastName() {
        return mLastName;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getEmail() {
        return mEmail;
    }

    public int getUserID() {
        return mUserID;
    }

     */

}
