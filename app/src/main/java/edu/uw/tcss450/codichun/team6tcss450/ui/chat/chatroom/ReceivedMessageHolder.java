package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatroom;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sendbird.android.message.UserMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.uw.tcss450.codichun.team6tcss450.R;

/**
 * @author codichun
 * @version 1.0
 */
public class ReceivedMessageHolder extends RecyclerView.ViewHolder {

    TextView messageText, timeText, nameText;
    //ImageView profileImage;

    public ReceivedMessageHolder(@NonNull View itemView) {
        super(itemView);
        messageText = itemView.findViewById(R.id.textview_chatroom_message_receiver);
        timeText = itemView.findViewById(R.id.textview_chatroom_timestamp_receiver);
        nameText = itemView.findViewById(R.id.textview_chatroom_name_receiver);
        //profileImage = itemView.findViewById(R.id.);
    }

    void bind(UserMessage message) {
        messageText.setText(message.getMessage());
        timeText.setText(formatDateTime(message.getCreatedAt()));
        nameText.setText(message.getSender().getNickname());
        // add display profile later?
    }

    /**
     * Convert the time to reable format
     * @param timestamp
     * @return formateed time
     */
    private String formatDateTime(long timestamp){
    // Convert the timestamp to a Date object
        Date date = new Date(timestamp);
    // Create a SimpleDateFormat object to format the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy, hh:mm a", Locale.getDefault());
    // Format the date as a string
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }
}
