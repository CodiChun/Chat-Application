package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatroom;

import android.view.View;
import android.widget.TextView;

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
public class SentMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText;
    SentMessageHolder(View itemView){
        super(itemView);
        messageText = itemView.findViewById(R.id.cardview_chatroom_message_sender);
        timeText = itemView.findViewById(R.id.textview_chatroom_timestamp_sender);
    }

    void bind(UserMessage message){
        messageText.setText(message.getMessage());
        timeText.setText(formatDateTime(message.getCreatedAt()));
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
