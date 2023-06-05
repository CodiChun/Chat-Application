package edu.uw.tcss450.codichun.team6tcss450.ui.contacts;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

import edu.uw.tcss450.codichun.team6tcss450.MainActivity;
import edu.uw.tcss450.codichun.team6tcss450.R;


public class AddFriendsRecyclerViewAdapter extends RecyclerView.Adapter<AddFriendsRecyclerViewAdapter.ViewHolder>{

    private final HashMap<Integer,Contact> mContacts;
    private ManageContactViewModel mManage;


    /**
     * Constructor
     * @param contacts current contacts
     */
    public AddFriendsRecyclerViewAdapter(HashMap<Integer,Contact> contacts){
        this.mContacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_contact_card,parent,false);
        mManage = new ViewModelProvider(
                (ViewModelStoreOwner) MainActivity.getActivity()).get(ManageContactViewModel.class);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = mContacts.get(position);
        holder.nickname.setText(contact.getUserName());

        holder.messageButton.setOnClickListener(Navigation.createNavigateOnClickListener
                (R.id.action_addFriendsFragment_to_chatRoomFragment));

        switch (contact.getStatus()){
            case RECEIVED_REQUEST:
                holder.addButton.setOnClickListener(button ->
                        showAcceptContactDialog(contact, holder.view, position));
                break;
            case NOT_CONNECTED:
                holder.addButton.setOnClickListener(button ->
                        showSentRequestDialog(contact, holder.view, position));
        }
    }


    void showAcceptContactDialog(Contact contact, View view, int position) {
        Dialog dialog = new Dialog(view.getContext());
        dialog.setCancelable(true);

        dialog.setContentView(R.layout.dialogue_contact_accept_request);
        dialog.findViewById(R.id.button_ok).setOnClickListener(button -> {
            dialog.dismiss();
            mManage.connectAcceptContacts(contact.getUserID());
            removeFromList(position);
            Toast.makeText(MainActivity.getActivity(),"Request Sent", Toast.LENGTH_SHORT).show();
        });
        dialog.findViewById(R.id.button_decline).setOnClickListener(button -> {
            dialog.dismiss();
            mManage.connectRemoveContact(contact.getUserID());
            removeFromList(position);
            Toast.makeText(MainActivity.getActivity(),"Request Declined", Toast.LENGTH_SHORT).show();
        });
        dialog.findViewById(R.id.button_cancel).setOnClickListener(button -> dialog.dismiss());
        dialog.show();
    }


    void showSentRequestDialog(Contact contact, View view, int position) {
        Dialog dialog = new Dialog(view.getContext());
        dialog.setCancelable(true);

        dialog.setContentView(R.layout.dialogue_contact_send_request);
        dialog.findViewById(R.id.button_ok).setOnClickListener(button -> {
            dialog.dismiss();
            mManage.connectSendRequest(contact.getUserID());
            removeFromList(position);
            Toast.makeText(MainActivity.getActivity(),"Request Sent", Toast.LENGTH_SHORT).show();
        });
        dialog.findViewById(R.id.button_cancel).setOnClickListener(button -> dialog.dismiss());
        dialog.show();
    }


    private void removeFromList(int position){
        mContacts.remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position, mContacts.size());
    }


    @Override
    public int getItemCount() {
        return mContacts.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView nickname;
        private final ImageView messageButton;
        private final ImageView addButton;
        private final View view;

        public ViewHolder(View itemView) {
            super(itemView);
            nickname = itemView.findViewById(R.id.contact_name);
            messageButton = itemView.findViewById(R.id.button_chat);
            addButton = itemView.findViewById(R.id.button_delete);
            view = itemView.getRootView();
        }
    }

}
