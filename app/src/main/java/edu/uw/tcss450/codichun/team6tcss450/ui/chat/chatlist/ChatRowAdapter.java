package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.codichun.team6tcss450.R;


/**
 * The Adappter for chat row
 * @author codichun
 * @version 1.0
 */
public class ChatRowAdapter extends RecyclerView.Adapter<ChatRowViewHolder> {

    Context context;
    List<ChatRow> rows;

    //new
    private OnItemClickListener listener;

    //new
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ChatRowAdapter(Context context, List<ChatRow> rows) {
        this.context = context;
        this.rows = rows;
    }

    @NonNull
    @Override
    public ChatRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatRowViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_chat_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRowViewHolder holder, int position) {
        ChatRow row = rows.get(position);
        holder.nameView.setText(rows.get(position).getmRoomName());

        //TODO: SHOW THE LAST MESSAGE
        //holder.messageView.setText(rows.get(position).g);

        holder.profileView.setImageResource(rows.get(position).getmProfile());

        //new
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(v, holder.getAdapterPosition());
                }
            }
        });

        // Show or hide the notification image based on the hasNewMessage property
        if (row.isHasNewMessage()) {
            holder.notificationImageView.setVisibility(View.VISIBLE);
        } else {
            holder.notificationImageView.setVisibility(View.GONE);
        }

        //set new message on the item
        holder.setMessageView(row.getmLastMessage());
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    /**
     * Interface for when an chat row item is clicked
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * get the position of a chat row item
     * @param position
     * @return
     */
    public ChatRow getDataAtPosition(int position){
        return rows.get(position);
    }

    /**
     * Update the chat list
     * @param newChatRow
     */
    public void updateData(List<ChatRow> newChatRow){
        rows = newChatRow;
        notifyDataSetChanged();
    }

    /**
     * Get the chat list
     * @return a list of chat rows
     */
    public List<ChatRow> getRows() {
        return rows;
    }
}