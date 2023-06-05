package edu.uw.tcss450.codichun.team6tcss450.ui.contacts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import edu.uw.tcss450.codichun.team6tcss450.MainActivity;
import edu.uw.tcss450.codichun.team6tcss450.R;
import edu.uw.tcss450.codichun.team6tcss450.databinding.FragmentContactBinding;
import edu.uw.tcss450.codichun.team6tcss450.databinding.FragmentContactListBinding;
import edu.uw.tcss450.codichun.team6tcss450.model.UserInfoViewModel;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ContactListFragment extends Fragment {

    private FragmentContactListBinding mBinding;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentContactListBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = mBinding.contactList;

        ContactListViewModel model = new ViewModelProvider((ViewModelStoreOwner)
                getActivity()).get(ContactListViewModel.class);
        UserInfoViewModel user = new ViewModelProvider((ViewModelStoreOwner)
                getActivity()).get(UserInfoViewModel.class);

        model.resetContacts();
        model.connectContacts(user.getUserId(),user.getmJwt(), "current");
        model.addContactListObserver(getViewLifecycleOwner(), this::setAdapter);

        mBinding.fabContactsSearch.setOnClickListener(button -> navigateToAddNewFriends());


    }

    /**
     * Set Adapter for the Contacts
     * @param contacts list of users contacts
     */
    private void setAdapter(List<Contact> contacts) {
        Log.d("ADAPTER", "PARA FROM ADAPTER: " + contacts.toString());
        HashMap<Integer, Contact> contactMap = new HashMap<>();
        for (Contact contact : contacts){
            contactMap.put(contacts.indexOf(contact), contact);
        }
        mRecyclerView.setAdapter(new ContactRecyclerViewAdapter(contactMap));
    }

    /**
     * Navigate to AddFriendsFragment
     */
    private void navigateToAddNewFriends() {
        Navigation.findNavController(getView()).navigate(ContactListFragmentDirections
                .actionNavigationContactlistToAddFriendsFragment());
    }

    /*
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
        //mContactAdapter = new ContactRecyclerViewAdapter(ContactListViewModel.connectContacts());
        binding.contactList.setAdapter(mContactAdapter);
    }


    public void addDeleteButtonListener(View view) {
        ImageButton buttonCancel = (ImageButton)view.findViewById(R.id.button_delete);
        buttonCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                deleteContact(view);
            }
        });
    }

    private void deleteContact(View view) {
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        return view;
    }
     */
}