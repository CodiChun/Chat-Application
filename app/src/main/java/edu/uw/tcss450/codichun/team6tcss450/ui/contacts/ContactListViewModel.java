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

    /*
    public void addContactsObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<Contact>> observer) {
        mContacts.observe(owner, observer);
    }

    public void setContacts(List<Contact> contacts){ mContacts.setValue(contacts);}

    public List<Contact> getContacts(){return mContacts.getValue();}

     */
}
