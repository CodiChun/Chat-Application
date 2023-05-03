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

    ImageView profileView;
    TextView nameView, messageView;

    public ChatRowViewHolder(@NonNull View itemView) {
        super(itemView);
        profileView = itemView.findViewById(R.id.imageview_chatroomview_profile);
        nameView = itemView.findViewById(R.id.textview_chatroomview_name);
        messageView = itemView.findViewById((R.id.textview_chatroomview_message));
    }
}
