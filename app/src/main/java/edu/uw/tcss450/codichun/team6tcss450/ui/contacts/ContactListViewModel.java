package edu.uw.tcss450.codichun.team6tcss450.ui.contacts;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

public class ContactListViewModel extends AndroidViewModel {

    //private MutableLiveData<List<Contact>> mContacts;

    public ContactListViewModel(@NonNull Application application) {
        super(application);
        //mContacts = new MutableLiveData<>();
        //mContacts.setValue(new ArrayList<>());
    }

}
