package edu.uw.tcss450.codichun.team6tcss450.ui.contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

import edu.uw.tcss450.codichun.team6tcss450.MainActivity;
import edu.uw.tcss450.codichun.team6tcss450.R;
import edu.uw.tcss450.codichun.team6tcss450.databinding.FragmentAddFriendsBinding;
import edu.uw.tcss450.codichun.team6tcss450.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class AddFriendsFragment extends Fragment {

    private FragmentAddFriendsBinding mBinding;
    private RecyclerView mReceivedRecyclerView, mSearchedRecyclerView;
    private UserInfoViewModel mUser;

    public AddFriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentAddFriendsBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUser = new ViewModelProvider((ViewModelStoreOwner) MainActivity.getActivity())
                .get(UserInfoViewModel.class);
        mReceivedRecyclerView = mBinding.listFriendRequests;
        mSearchedRecyclerView = mBinding.listSearchPeople;

        mBinding.buttonSearchPeople.setOnClickListener(button -> displaySearched());

        mUser = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        ContactListViewModel getRequests = new ViewModelProvider(
                (ViewModelStoreOwner) MainActivity.getActivity()).get(ContactListViewModel.class);
        getRequests.resetRequests();
        getRequests.connectContacts(mUser.getUserId(), mUser.getmJwt(), "requests");
        getRequests.addPendingListObserver(getViewLifecycleOwner(), this::setAdapterForRequests);
    }

    /**
     * display the result of Search People function
     */
    private void displaySearched(){
        mBinding.editSearchPeople.setError(null);
        if (mBinding.editSearchPeople.getText().toString().equals("")) {
            mBinding.editSearchPeople.setError("Cannot be Empty");
            return;
        }

        SearchViewModel searchResult = new ViewModelProvider(
                (ViewModelStoreOwner) MainActivity.getActivity()).get(SearchViewModel.class);
        searchResult.resetSearchResults();
        searchResult.connectSearch(mUser.getmJwt(), mBinding.editSearchPeople.getText().toString());
        searchResult.addSearchListObserver(getViewLifecycleOwner(), this::setAdapterForSearch);
    }

    /**
     * set adapter for Search People
     * @param contacts the list of results after Search People
     */
    private void setAdapterForSearch(List<Contact> contacts) {
        HashMap<Integer, Contact> contactMap = new HashMap<>();
        for (Contact contact : contacts) {
            contactMap.put(contacts.indexOf(contact), contact);
        }
        //mSearchedRecyclerView.setAdapter(new AddFriendsRecyclerViewAdapter(contactMap));
    }

    /**
     * set adapter for Friend Requests
     * @param contacts the list of the friend requests
     */
    private void setAdapterForRequests(List<Contact> contacts) {
        HashMap<Integer, Contact> contactMap = new HashMap<>();
        for (Contact contact : contacts) {
            contactMap.put(contacts.indexOf(contact), contact);
        }
        mReceivedRecyclerView.setAdapter(new AddFriendsRecyclerViewAdapter(contactMap));
        mUser.setIncomingRequests(mReceivedRecyclerView.getAdapter().getItemCount());
    }
}