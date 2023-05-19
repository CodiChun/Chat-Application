package edu.uw.tcss450.codichun.team6tcss450.ui.contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.uw.tcss450.codichun.team6tcss450.databinding.FragmentContactCardBinding;

public class ContactCardFragment extends Fragment {

    private FragmentContactCardBinding binding;

    private static final String mParam1 = "test1";
    private static final String mParam2 = "test2";

    private String m1;
    private String m2;

    public ContactCardFragment() {
        // Required empty public constructor
    }


    public static ContactCardFragment newInstance(String m1, String m2) {
        ContactCardFragment fragment = new ContactCardFragment();
        Bundle args = new Bundle();
        args.putString(mParam1, m1);
        args.putString(mParam2, m2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            m1 = getArguments().getString(mParam1);
            m2 = getArguments().getString(mParam2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //binding = FragmentContactCardBinding.inflate(inflater);
        // Inflate the layout for this fragment
        //return binding.getRoot();
        return inflater.inflate(edu.uw.tcss450.codichun.team6tcss450.R.layout.fragment_contact_card, container, false);
    }

    /*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

     */
}
