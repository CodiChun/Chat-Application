package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sendbird.android.SendbirdChat;
import com.sendbird.android.message.BaseMessage;
import com.sendbird.android.message.UserMessage;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.uw.tcss450.codichun.team6tcss450.R;

public class MessageListAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private Context mContext;
    private List<BaseMessage> mMessageList;

    public MessageListAdapter(Context context, List<BaseMessage> messageList) {
        mContext = context;
        mMessageList = messageList;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        UserMessage message = (UserMessage) mMessageList.get(position);
//SendBird.getCurrentUser().getUserId())
        if (message.getSender().getUserId().equals(SendbirdChat.getCurrentUser().getUserId())) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_sender_message, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_receiver_message, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserMessage message = (UserMessage) mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_chatroom_message_sender);
            timeText = (TextView) itemView.findViewById(R.id.textview_chatroom_timestamp_sender);
        }

        void bind(UserMessage message) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
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

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.textview_chatroom_message_receiver);
            timeText = (TextView) itemView.findViewById(R.id.textview_chatroom_timestamp_receiver);
            nameText = (TextView) itemView.findViewById(R.id.textview_chatroom_message_receiver);
            //profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
        }

        void bind(UserMessage message) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(formatDateTime(message.getCreatedAt()));

            nameText.setText(message.getSender().getNickname());

            // Insert the profile image from the URL into the ImageView.
            //Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
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
}