package edu.uw.tcss450.codichun.team6tcss450.ui.chat;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRowViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
