package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatroom;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.codichun.team6tcss450.MainActivity;
import edu.uw.tcss450.codichun.team6tcss450.R;

public class RoomInfoMemberAdapter extends RecyclerView.Adapter<RoomInfoMemberAdapter.RoomInfoMemberViewHolder> {

    private List<RoomInfoMember> members;
    private Context mContext;

    public RoomInfoMemberAdapter(Context context) {
        this.members = new ArrayList<>();
        this.mContext = context;
    }

    public RoomInfoMemberAdapter(List<RoomInfoMember> mMembersList, Context context) {
        this.members = mMembersList;
        this.mContext = context;
    }


    @NonNull
    @Override
    public RoomInfoMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_roominfo, parent, false);
        return new RoomInfoMemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomInfoMemberViewHolder holder, int position) {
        RoomInfoMember member = members.get(position);

        holder.memberNameTextView.setText(member.getEmail());
        holder.removeMemberButton.setOnClickListener(v -> {
            // Remove the member from the members list
            members.remove(position);

            if (members.isEmpty()) {
                //TODO: HANDLE WHEN THE LAST MEMBER WAS REMOVED, NOW THE APP CRUSHES
                notifyDataSetChanged();
                navigateToChatRoomListFragment();

            } else {
                // Notify the adapter that an item was removed
                notifyItemRemoved(position);

                //notify that the data range has changed
                notifyItemRangeChanged(position, members.size());
            }

            //remove database
            RoomInfoMember selected = members.get(position);

            mListener.onRemoveMember(selected.getEmail());

        });
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public void setMembers(List<RoomInfoMember> members) {
        this.members = members;
        notifyDataSetChanged(); // Notify the adapter that the data has changed so it can update the RecyclerView
    }

    public static class RoomInfoMemberViewHolder extends RecyclerView.ViewHolder {
        TextView memberNameTextView;
        ImageButton removeMemberButton; // Here, changed Button to ImageButton

        public RoomInfoMemberViewHolder(@NonNull View itemView) {
            super(itemView);
            memberNameTextView = itemView.findViewById(R.id.TextView_itemmemberchatinfo_membername);
            removeMemberButton = itemView.findViewById(R.id.imageButton_itemmemberchatinfo_remove);
        }
    }


    public interface OnRemoveMemberListener {
        void onRemoveMember(String position);
    }

    private OnRemoveMemberListener mListener;

    public RoomInfoMemberAdapter(OnRemoveMemberListener listener) {
        this.members = new ArrayList<>();
        this.mListener = listener;
    }

    private void navigateToChatRoomListFragment() {
        // Create an Intent to navigate to the ChatRoomListFragment
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra("fragment", "chatRoomList");
        mContext.startActivity(intent);
    }


}
