package edu.uw.tcss450.codichun.team6tcss450.ui.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.codichun.team6tcss450.R;

/**
 * create an instance of this fragment.
 * @author codichun
 * @version 1.0
 */
public class ChatRoomListFragment extends Fragment {

    public ChatRoomListFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_chat_room_list, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview_chatList);

         //Mock data for testing, can be changed to database later
        List<ChatRow> rows = new ArrayList<ChatRow>();
        rows.add(new ChatRow("Codi", "see ya", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("Julia", "ok", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("JD", "NP", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("Majed", "It works", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("Codi", "see ya", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("Julia", "ok", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("JD", "NP", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("Majed", "It works", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("Codi", "see ya", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("Julia", "ok", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("JD", "NP", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("Majed", "It works", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("Codi", "see ya", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("Julia", "ok", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("JD", "NP", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("Majed", "It works", R.drawable.image_chatlist_profile_32dp));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ChatRowAdapter(getActivity(),rows));
        return rootView;
    }
}