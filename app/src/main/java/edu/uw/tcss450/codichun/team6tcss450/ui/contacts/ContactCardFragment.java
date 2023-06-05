package edu.uw.tcss450.codichun.team6tcss450.ui.contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.sendbird.android.user.User;

import java.util.HashMap;
import java.util.List;

import edu.uw.tcss450.codichun.team6tcss450.MainActivity;
import edu.uw.tcss450.codichun.team6tcss450.databinding.FragmentContactCardBinding;
import edu.uw.tcss450.codichun.team6tcss450.databinding.FragmentContactListBinding;
import edu.uw.tcss450.codichun.team6tcss450.model.UserInfoViewModel;

public class ContactCardFragment extends Fragment {

    //private FragmentContactCardBinding mBinding;
    private FragmentContactListBinding mBinding;
    private RecyclerView mRecyclerView;



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
        mBinding = FragmentContactListBinding.inflate(inflater);
        // Inflate the layout for this fragment
        return mBinding.getRoot();
        //return inflater.inflate(edu.uw.tcss450.codichun.team6tcss450.R.layout.fragment_contact_card, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = mBinding.contactList;

        ContactListViewModel contactModel = new ViewModelProvider((ViewModelStoreOwner)MainActivity.getActivity()).get(ContactListViewModel.class);
        UserInfoViewModel userModel = new ViewModelProvider((ViewModelStoreOwner)MainActivity.getActivity()).get(UserInfoViewModel.class);

        contactModel.resetContacts();
        contactModel.connectContacts(userModel.getUserId(), userModel.getmJwt(), "current");
        //contactModel.addContactListObserver(getViewLifecycleOwner(), this::setAdapter);

        //mBinding.fabContactsSearch.setOnClickListener(button -> navigateToSearchContacts());
    }
    /*
    private void setAdapter(List<Contact> contactList) {
        Hashmap<Integer, Contact> contactHashmap = new HashMap<>();
        for (Contact contact : contactList){
            contactMap.put(contactList.indexOf(contact), contact);
        }
        mRecyclerView.setAdapter(new ContactRecyclerViewAdapter(contactHashmap));

    }

     */

    /*
    private void navigateToAddNewFriends() {
        Navigation.findNavController(getView()).navigate(ContactListFragmentDirections
                .actionContactsFragmentToAddFriendsFragment());
    }

     */
}
