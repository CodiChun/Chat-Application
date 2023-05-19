package edu.uw.tcss450.codichun.team6tcss450.ui.contacts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.navigation.Navigation;

import edu.uw.tcss450.codichun.team6tcss450.R;
import edu.uw.tcss450.codichun.team6tcss450.databinding.FragmentContactBinding;
import edu.uw.tcss450.codichun.team6tcss450.databinding.FragmentContactListBinding;
import edu.uw.tcss450.codichun.team6tcss450.model.UserInfoViewModel;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ContactListFragment extends Fragment {

    private UserInfoViewModel mInfoViewModel;
    private ContactListViewModel mContactListModel;
    private ContactRecyclerViewAdapter mContactAdapter;

    private String mEmail;

    public ContactListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ViewModelProvider provider = new ViewModelProvider(requireActivity());

        //mInfoViewModel = provider.get(UserInfoViewModel.class);
        //mContactModel = new ViewModelProvider(requireActivity()).get(ContactListViewModel.class);
        //mEmail = mInfoViewModel.getEmail();
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        FragmentContactListBinding binding = FragmentContactListBinding.bind(getView());
        mContactAdapter = new ContactRecyclerViewAdapter(ContactGenerator.getContactsList());
        binding.listRoot.setAdapter(mContactAdapter);
    }

    /*
    private void deleteContact(View view) {
        ContactListFragmentArgs args = ContactListFragmentArgs.fromBundle(getArguments());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to delete " + args.getContacts().getFirstName() + " " + args.getContacts().getLastName() + "?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mContactModel.deleteContact(mInfoViewModel.getJwt(), mInfoViewModel.getEmail(), args.getContacts().getEmail());
                        getActivity().onBackPressed();
                    }
                })
                .setNegativeButton("No", null);
        AlertDialog alert = builder.create();
        alert.show();
    }

     */

    /*
    //private FragmentContactBinding binding;

    private ContactViewModel mModel;
    //private UserInfoViewModel mInfoViewModel;
    //private int mChatID;
    //private boolean mThroughChat;


    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ContactViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        return view;
    }
     */
}