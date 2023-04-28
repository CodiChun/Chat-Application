package edu.uw.tcss450.codichun.team6tcss450.ui.chat;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.uw.tcss450.codichun.team6tcss450.R;

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
