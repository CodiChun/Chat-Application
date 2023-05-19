package edu.uw.tcss450.codichun.team6tcss450.ui.contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.uw.tcss450.codichun.team6tcss450.databinding.FragmentContactBinding;
import edu.uw.tcss450.codichun.team6tcss450.model.UserInfoViewModel;

public class ContactFragment extends Fragment {
    private UserInfoViewModel mUserInfoModel;
    private ContactListViewModel mContactListModel;
    private String mEmail;

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(requireActivity());

        mUserInfoModel = provider.get(UserInfoViewModel.class);
        mContactListModel = new ViewModelProvider(requireActivity()).get(ContactListViewModel.class);
        //mEmail = mUserInfoModel.getEmail();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //binding = FragmentContactBinding.inflate(inflater);
        // Inflate the layout for this fragment
        //return binding.getRoot();
        return inflater.inflate(edu.uw.tcss450.codichun.team6tcss450.R.layout.fragment_contact, view, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentContactBinding binding = FragmentContactBinding.bind(getView());
        //ContactFragmentArgs args = ContactFragmentArgs.fromBundle(getArguments());

        //mContactListModel.connectGet(mEmail);
    }
}
