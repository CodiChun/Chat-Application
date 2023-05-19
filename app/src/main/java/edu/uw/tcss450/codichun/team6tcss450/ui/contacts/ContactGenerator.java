package edu.uw.tcss450.codichun.team6tcss450.ui.contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Arrays;
import java.util.List;

import edu.uw.tcss450.codichun.team6tcss450.databinding.FragmentContactCardBinding;

public class ContactGenerator extends Fragment {
    private static final Contact[] CONTACTS;
    //demo data
    private static final String[] testNames = {"Laquan Shideh","Bisera Dafne","Tim Reese","Augusta Vincent","Tara Horton","Marlon Stanley",
            "Broderick Richards","Corina Summers","Conrad Harris","Paris Blanchard","Bridgette Strong","Maricela Gordon","Erwin Sherman",
            "Priscilla Combs","Jasmine Davies","Murray Farmer","Donny Olson","Elena Burnett","Anderson Olson","Lupe Baxter"};

    // creates a list of contact cards
    static {
        CONTACTS = new Contact[testNames.length];
        for (int i = 0; i < CONTACTS.length; i++) {
            CONTACTS[i] = new Contact.Builder(testNames[i]).build();
        }
    }

    public static Contact[] removeContacts(Contact[] contacts, int index) {
        Contact[] newContacts = new Contact[contacts.length - 1];
        for (int i = 0; i < contacts.length; i++) {
            if (i < index) {
                newContacts[i] = contacts[i];
            } else if (i > index) {
                newContacts[i - 1] = contacts[i];
            }
        }
        return newContacts;
    }

    // returns a list of contact cards
    public static List<Contact> getContactsList() {return Arrays.asList(CONTACTS);}

    public static Contact[] getContacts() {return Arrays.copyOf(CONTACTS, CONTACTS.length);}

    private ContactGenerator() {}

}
