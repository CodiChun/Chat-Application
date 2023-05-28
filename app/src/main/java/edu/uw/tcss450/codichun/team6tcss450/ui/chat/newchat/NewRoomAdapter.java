package edu.uw.tcss450.codichun.team6tcss450.ui.chat.newchat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sendbird.android.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.uw.tcss450.codichun.team6tcss450.MainActivity;
import edu.uw.tcss450.codichun.team6tcss450.R;
import edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatroom.RoomInfoMember;
import edu.uw.tcss450.codichun.team6tcss450.ui.chat.newchat.NewChatFragment;

public class NewRoomAdapter extends RecyclerView.Adapter<NewRoomAdapter.RoomInfoMemberViewHolder> {

    private List<RoomInfoMember> members;
    private Context mContext;
    List<Integer> mSelectedList;


    public NewRoomAdapter(Context context) {
        this.members = new ArrayList<>();
        this.mContext = context;
        this.mSelectedList = new ArrayList<>();
    }

    public NewRoomAdapter(List<RoomInfoMember> mMembersList, Context context, int userId) {
        this.members = mMembersList;
        this.mContext = context;
        mSelectedList = new ArrayList<>();
        mSelectedList.add(userId);
    }

    @NonNull
    @Override
    public NewRoomAdapter.RoomInfoMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_newroom, parent, false);
        return new NewRoomAdapter.RoomInfoMemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewRoomAdapter.RoomInfoMemberViewHolder holder, int position) {
        RoomInfoMember member = members.get(position);

        holder.memberNameTextView.setText(member.getEmail());

        // Reset the checkbox state
        holder.selectBox.setOnCheckedChangeListener(null);

        // If the current item was selected, check the box
        if (mSelectedList.contains(member.getMemberId())) {
            holder.selectBox.setChecked(true);
        } else {
            holder.selectBox.setChecked(false);
        }

        // Listen for checkbox check/uncheck
        holder.selectBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mSelectedList.add(member.getMemberId());
            } else {
                mSelectedList.remove((Integer) member.getMemberId());
            }
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

    // Getter method to get the selected members outside of the adapter
    public List<Integer> getSelectedMembers() {
        return mSelectedList;
    }

    public static class RoomInfoMemberViewHolder extends RecyclerView.ViewHolder {
        TextView memberNameTextView;
        CheckBox selectBox;

        public RoomInfoMemberViewHolder(@NonNull View itemView) {
            super(itemView);
            memberNameTextView = itemView.findViewById(R.id.TextView_itemmembernewroom_membername);
            selectBox = itemView.findViewById(R.id.checkbox_newchat_select);
        }
    }
}
