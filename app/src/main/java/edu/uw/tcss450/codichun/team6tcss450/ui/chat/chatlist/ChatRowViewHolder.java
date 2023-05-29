package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatlist;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.uw.tcss450.codichun.team6tcss450.R;

/**
 * The view holder of chat row
 * @author codichun
 * @version 1.0
 */
public class ChatRowViewHolder extends RecyclerView.ViewHolder {

    ImageView profileView, notificationImageView;
    TextView nameView, messageView;


    /**
     * Constructor
     * @param itemView
     */
    public ChatRowViewHolder(@NonNull View itemView) {
        super(itemView);
        profileView = itemView.findViewById(R.id.imageview_chatroomview_profile);
        nameView = itemView.findViewById(R.id.textview_chatroomview_name);
        messageView = itemView.findViewById((R.id.textview_chatroomview_message));
        notificationImageView = itemView.findViewById(R.id.ImageView_chatrow_notification);
    }

    public ImageView getProfileView() {
        return profileView;
    }

    public void setProfileView(ImageView profileView) {
        this.profileView = profileView;
    }

    public TextView getNameView() {
        return nameView;
    }

    public void setNameView(TextView nameView) {
        this.nameView = nameView;
    }

    public TextView getMessageView() {
        return messageView;
    }

    public void setMessageView(String message) {
        this.messageView.setText(message);
    }

}
