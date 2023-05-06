package edu.uw.tcss450.codichun.team6tcss450.ui.contacts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.codichun.team6tcss450.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ContactsListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);
        return view;
    }
}