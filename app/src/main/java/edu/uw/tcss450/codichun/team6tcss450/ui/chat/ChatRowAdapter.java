package edu.uw.tcss450.codichun.team6tcss450.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.codichun.team6tcss450.R;

public class ChatRowAdapter extends RecyclerView.Adapter<ChatRowViewHolder> {

    Context context;
    List<ChatRow> rows;

    public ChatRowAdapter(Context context, List<ChatRow> rows) {
        this.context = context;
        this.rows = rows;
    }

    @NonNull
    @Override
    public ChatRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatRowViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_chat_room_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRowViewHolder holder, int position) {
        holder.nameView.setText(rows.get(position).getName());
        holder.messageView.setText(rows.get(position).getMessage());
        holder.profileView.setImageResource(rows.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }
}
