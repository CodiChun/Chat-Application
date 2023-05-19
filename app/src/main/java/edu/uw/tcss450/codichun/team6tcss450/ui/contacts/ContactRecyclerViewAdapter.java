package edu.uw.tcss450.codichun.team6tcss450.ui.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.uw.tcss450.codichun.team6tcss450.R;
import edu.uw.tcss450.codichun.team6tcss450.databinding.FragmentContactCardBinding;

public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ContactViewHolder> {

    private final List<Contact> mContacts;
    //Store the expanded state for each List item, true -> expanded, false -> not

    //private final Map<Contact, Boolean> mExpandedFlags;



    public ContactRecyclerViewAdapter(List<Contact> items) {
        this.mContacts = items;

        //mExpandedFlags = mContacts.stream()
                //.collect(Collectors.toMap(Function.identity(), contact -> false));
    }



    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // inflates contact card for recycler view
        return new ContactViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_contact_card, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.setContact(mContacts.get(position));

        // sets on click listener for each contact card
/*        holder.binding.cardRoot.setOnClickListener(button -> {
            Navigation.findNavController(holder.mView).navigate(
                    ContactFragmentDirections.actionNavigationConnectionsToContactPageFragment(mContacts.get(position))
            );
            Log.i("Button","Pressed");
        });*/

    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public boolean addContact(Contact contact){
        return mContacts.add(contact);
    }


    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Blog Recycler View.
     */
    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentContactCardBinding binding;

        private Contact mContact;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            binding = FragmentContactCardBinding.bind(itemView);
        }


        void setContact(final Contact contact) {
            mContact = contact;
            //binding.textContactUser.setText(contact.getName());

            binding.textContact.setText(contact.getFirstName());

        }

    }
}
