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


    public ContactCardFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
